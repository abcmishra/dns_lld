class IPRecord {
    private final String ipAddress;
    private final long creationTime;
    private final long ttlMillis;
    private volatile boolean healthy = true;

    public IPRecord(String ipAddress, long creationTime, long ttlMillis) {
        this.ipAddress = ipAddress;
        this.creationTime = creationTime;
        this.ttlMillis = ttlMillis;
    }

    public boolean isExpired(long now) {
        return now > creationTime + ttlMillis;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
