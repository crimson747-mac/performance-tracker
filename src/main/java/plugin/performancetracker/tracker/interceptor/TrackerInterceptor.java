package plugin.performancetracker.tracker.interceptor;

import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import plugin.performancetracker.service.InfluxService;
import plugin.performancetracker.tracker.PerformanceTracker;
import plugin.performancetracker.tracker.TrackData;
import plugin.performancetracker.tracker.TrackerContext;
import plugin.performancetracker.tracker.TrackerPool;
import plugin.performancetracker.utils.PointBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class TrackerInterceptor implements HandlerInterceptor {

    @Autowired InfluxService INFLUX_SERVICE;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ThreadLocal<PerformanceTracker> threadLocal = TrackerContext.THREAD_LOCAL;
        PerformanceTracker tracker = TrackerPool.get();

        if (tracker != null) threadLocal.set(tracker.init());
        else threadLocal.set(null);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocal<PerformanceTracker> threadLocal = TrackerContext.THREAD_LOCAL;
        PerformanceTracker tracker = threadLocal.get();
        if(tracker != null && tracker.getTrackDataStack().size() > 0) {
            List<TrackData> trackDataStack = tracker.getTrackDataStack();
            List<Point> trackerPoints = PointBuilder.getTrackerPoints(trackDataStack, tracker.getTraceId(), tracker.getTime());
            INFLUX_SERVICE.insert(trackerPoints);
            TrackerPool.retrieve(tracker);
        }
        TrackerContext.clear();
    }
}
