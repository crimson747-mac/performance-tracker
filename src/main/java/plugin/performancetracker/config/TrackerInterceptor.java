package plugin.performancetracker.config;

import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import plugin.performancetracker.tracker.service.InfluxService;
import plugin.performancetracker.tracker.PerformanceTracker;
import plugin.performancetracker.tracker.TrackData;
import plugin.performancetracker.tracker.TrackerContext;
import plugin.performancetracker.tracker.TrackerPool;
import plugin.performancetracker.utils.PointBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class TrackerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return TrackerContext.init();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TrackerContext.clear();
    }
}
