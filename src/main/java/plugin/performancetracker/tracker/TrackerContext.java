package plugin.performancetracker.tracker;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.influxdb.dto.Point;
import plugin.performancetracker.tracker.service.InfluxService;
import plugin.performancetracker.utils.PointBuilder;

import java.util.List;

public class TrackerContext {

    public static ThreadLocal<PerformanceTracker> THREAD_LOCAL = new ThreadLocal<>();

    public static InfluxService INFLUX_SERVICE = new InfluxService();

    public static boolean init() {
        PerformanceTracker tracker = TrackerPool.get();

        if (tracker != null) THREAD_LOCAL.set(tracker.init());
        else THREAD_LOCAL.set(null);

        return true;
    }

    public static void clear() {
        PerformanceTracker tracker = THREAD_LOCAL.get();
        if(tracker != null && tracker.getTrackDataStack().size() > 0) {
            List<TrackData> trackDataStack = tracker.getTrackDataStack();
            List<Point> trackerPoints = PointBuilder.getTrackerPoints(trackDataStack, tracker.getTraceId(), tracker.getTotalConsumeTime());
            INFLUX_SERVICE.insert(trackerPoints);
            TrackerPool.retrieve(tracker);
        }
        THREAD_LOCAL.remove();
    }

    public static Object saveFunctionCallInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        PerformanceTracker tracker = TrackerContext.THREAD_LOCAL.get();
        if(tracker == null) {
            return joinPoint.proceed();
        } {
            long start = System.currentTimeMillis();
            try {
                return joinPoint.proceed();
            } finally {
                TrackData trackData = new TrackData(getClassWithMethodName(joinPoint));
                trackData.setConsumeMillis(System.currentTimeMillis() - start);
                tracker.addTraceData(trackData);
            }
        }
    }

    private static String getClassWithMethodName(ProceedingJoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();

        return sb
                .append(getClassName(joinPoint))
                .append(".")
                .append(getMethodName(joinPoint))
                .toString();
    }

    private static String getClassName(ProceedingJoinPoint joinPoint) {
        return joinPoint.getTarget().getClass().getSimpleName();
    }

    private static String getMethodName(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().getName();
    }
}
