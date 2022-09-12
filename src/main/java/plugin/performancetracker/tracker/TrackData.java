package plugin.performancetracker.tracker;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackData {
    private String classMethodName;
    private long consumeMillis;

    public TrackData(String classMethodName) {
        this.classMethodName = classMethodName;
    }
}
