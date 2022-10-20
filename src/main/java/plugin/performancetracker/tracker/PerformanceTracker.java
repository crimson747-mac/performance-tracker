package plugin.performancetracker.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PerformanceTracker {

    private int number;
    private String traceId;
    private long totalConsumeTime;
    private List<TrackData> trackDataStack;

    public PerformanceTracker(int number) {
        this.number = number;
    }

    public PerformanceTracker reset() {
        this.traceId = null;
        this.totalConsumeTime = 0L;
        this.trackDataStack = null;
        return this;
    }

    public PerformanceTracker init() {
        this.traceId = UUID.randomUUID().toString();
        this.totalConsumeTime = System.currentTimeMillis();
        this.trackDataStack = new ArrayList<>(5);
        return this;
    }

    public void addTraceData(TrackData data) {
        this.trackDataStack.add(data);
    }

    // ========== Getter ========== //

    public int getNumber() {
        return number;
    }

    public String getTraceId() {
        return traceId;
    }

    public long getTotalConsumeTime() {
        return totalConsumeTime;
    }

    public List<TrackData> getTrackDataStack() {
        return trackDataStack;
    }
}
