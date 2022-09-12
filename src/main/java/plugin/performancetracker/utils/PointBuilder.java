package plugin.performancetracker.utils;

import org.influxdb.dto.Point;
import plugin.performancetracker.tracker.TrackData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PointBuilder {

    public static List<Point> getTrackerPoints(List<TrackData> trackDataStack, String traceId, long time) {
        TrackData last = trackDataStack.get(trackDataStack.size() - 1);
        List<Point> result = new ArrayList<>();
        long beforeConsumeMillis = 0;

        for (TrackData trackData : trackDataStack) {
            long consumeMillis = trackData.getConsumeMillis();
            Point point = Point.measurement("memberService")
                    .time(time, TimeUnit.MILLISECONDS)
                    .tag("trace_id", traceId)
                    .tag("class.method", trackData.getClassMethodName())
                    .addField("consume_millis", consumeMillis - beforeConsumeMillis)
                    .addField("total_millis", last.getConsumeMillis())
                    .build();
            result.add(point);

            beforeConsumeMillis = consumeMillis;
        }

        return result;
    }
}
