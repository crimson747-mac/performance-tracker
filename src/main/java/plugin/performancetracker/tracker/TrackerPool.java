package plugin.performancetracker.tracker;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TrackerPool {

    private static final Queue<PerformanceTracker> pool = new ConcurrentLinkedQueue<>();

    private static boolean isInitialized = false;

    public static PerformanceTracker get() {
        if (!isInitialized) {
            initialize(null);
            isInitialized = true;
        }

        return pool.size() > 0 ? pool.poll() : null;
    }

    private static void initialize(Integer poolSize) {
        if(poolSize == null || poolSize == 0) {
            for (int i = 0; i < 50; i++) createTrackerToPool(i);
        } else {
            for (int i = 0; i < poolSize; i++) createTrackerToPool(i);
        }
    }

    private static void createTrackerToPool(int i) {
        PerformanceTracker performanceTracker = new PerformanceTracker(i);
        pool.add(performanceTracker);
    }

    public static  void retrieve(PerformanceTracker tracker) {
        System.out.println(tracker.getNumber() + " is retrieved");
        pool.add(tracker.reset());
    }
}
