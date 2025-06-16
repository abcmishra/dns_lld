import java.util.List;

class DnsResolver {
    private final DnsCache cache;
    private final HealthChecker healthChecker;

    public DnsResolver(DnsCache cache, HealthChecker checker) {
        this.cache = cache;
        this.healthChecker = checker;
    }

    public String resolve(String domain) {
        List<IPRecord> records = cache.getValidIPs(domain);
        for (IPRecord ip : records) {
            if (ip.isHealthy() && !ip.isExpired()) {
                return ip.getIpAddress(); // Round-robin or random strategy
            }
        }
        // Fallback: resolve externally (simulate with stub)
        IPRecord newRecord = fetchFromExternalDns(domain);
        cache.insert(domain, newRecord);
        return newRecord.getIpAddress();
    }

    private IPRecord fetchFromExternalDns(String domain) {
        // Simulate DNS resolution
        String ip = ExternalDnsService.resolve(domain);
        return new IPRecord(ip, System.currentTimeMillis(), 60_000); // TTL = 60s
    }
}
