package plugin.performancetracker.tracker;

public class TrackerContext {

    public static ThreadLocal<PerformanceTracker> THREAD_LOCAL = new ThreadLocal<>();

    public static void clear() {
        THREAD_LOCAL.remove();
    }

}
