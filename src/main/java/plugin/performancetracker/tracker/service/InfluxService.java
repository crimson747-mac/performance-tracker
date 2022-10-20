package plugin.performancetracker.tracker.service;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.util.List;

public class InfluxService {

    private String serverUrl;
    private String databaseName;
    private String username;
    private String password;
    private InfluxDB influxDB;

    public InfluxService() {
        this.serverUrl = "http://localhost:8086";
        this.databaseName = "performance";
        this.username = "zenon84850";
        this.password = "zenon84850";
        this.influxDB = InfluxDBFactory
                .connect(this.serverUrl, this.username, this.password)
                .setDatabase(this.databaseName);
    }

    public void insert(List<Point> gcUtilPoints) {
        for (Point point : gcUtilPoints) {
            influxDB.write(point);
        }
    }
}
