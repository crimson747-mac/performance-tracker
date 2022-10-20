package plugin.performancetracker.tracker;

public class TrackData {
    private String classMethodName;
    private long consumeMillis;

    public TrackData(String classMethodName) {
        this.classMethodName = classMethodName;
    }

    // ========== Getter ========== //

    public String getClassMethodName() {
        return classMethodName;
    }

    public long getConsumeMillis() {
        return consumeMillis;
    }

    public void setConsumeMillis(long consumeMillis) {
        this.consumeMillis = consumeMillis;
    }
}
