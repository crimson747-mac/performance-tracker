package plugin.performancetracker.tracker;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TrackerPool {

    private static final Queue<PerformanceTracker> pool = new ConcurrentLinkedQueue<>();

    private static boolean isInitialized = false;

    private static void initialize(int poolSize) {
        for (int i = 0; i < poolSize; i++) {
            PerformanceTracker performanceTracker = new PerformanceTracker(i);
            pool.add(performanceTracker);
        }
    }

    public static PerformanceTracker get() {
        if (!isInitialized) {
            initialize(50);
            isInitialized = true;
        }

        return pool.size() > 0 ? pool.poll() : null;
    }

    public static  void retrieve(PerformanceTracker tracker) {
        System.out.println(tracker.getNumber() + " is retrieved");
        pool.add(tracker.reset());
    }
}
