import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class DnsCache {
    private final ConcurrentHashMap<String, List<IPRecord>> cache = new ConcurrentHashMap<>();

    public List<IPRecord> getValidIPs(String domain) {
        List<IPRecord> records = cache.get(domain);
        if (records == null) return Collections.emptyList();

        long now = System.currentTimeMillis();
        return records.stream()
                .filter(ip -> !ip.isExpired(now))
                .collect(Collectors.toList());
    }

    public void insert(String domain, IPRecord record) {
        cache.compute(domain, (d, existing) -> {
            if (existing == null) existing = new ArrayList<>();
            existing.add(record);
            return existing;
        });
    }

    public Map<Object, Object> getAllEntries() {
    }
}
