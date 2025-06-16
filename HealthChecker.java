import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class HealthChecker {
    private final DnsCache cache;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public HealthChecker(DnsCache cache) {
        this.cache = cache;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::checkAll, 0, 30, TimeUnit.SECONDS);
    }

    private void checkAll() {
        for (Map.Entry<String, List<IPRecord>> entry : cache.getAllEntries().entrySet()) {
            for (IPRecord record : entry.getValue()) {
                boolean healthy = ping(record.getIpAddress());
                record.setHealthy(healthy);
            }
        }
    }

    private boolean ping(String ipAddress) {
        // Simplified mock logic
        return Math.random() > 0.1; // 90% chance it's healthy
    }
}
