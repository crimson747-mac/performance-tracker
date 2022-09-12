package plugin.performancetracker.tracker;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class PerformanceTracker {

    private int number;
    private String traceId;
    private long time;
    private List<TrackData> trackDataStack;

    public PerformanceTracker(int number) {
        this.number = number;
    }

    public PerformanceTracker reset() {
        this.traceId = null;
        this.time = 0L;
        this.trackDataStack = null;
        return this;
    }

    public PerformanceTracker init() {
        this.traceId = UUID.randomUUID().toString();
        this.time = System.currentTimeMillis();
        this.trackDataStack = new ArrayList<>(5);
        return this;
    }

    public void addTraceData(TrackData data) {
        this.trackDataStack.add(data);
    }
}
