package main.readers;

import main.model.StopTime;
import main.util.TimeParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads and filters GTFS stop time data from the stop_times.txt file.
 */
public class StopTimeReader {

    private static final int LIMIT_TIME_MINUTES = 120;

    /**
     * Parses stop times for a given stop ID that occur within the next
     * {@value #LIMIT_TIME_MINUTES} minutes.
     *
     * @param stopId     the stop ID to filter by
     * @param nowMinutes the current time in minutes
     * @param stopTimesPath path to stop times text file
     * @return a list of {@link StopTime} objects matching the stop ID and time window;
     */
    public List<StopTime> parseStopTimeByStopId(int stopId, int nowMinutes, String stopTimesPath) {
        List<StopTime> stopTimeList = new ArrayList<>();
        int maxTimeMinutes = nowMinutes + LIMIT_TIME_MINUTES;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(stopTimesPath)));
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] parts = line.split(",");
                int lineStopId = Integer.parseInt(parts[3]);
                if (stopId != lineStopId) {
                    continue;
                }

                int arrivalTimeMinutes = TimeParser.toMinutes(parts[1]);
                if (arrivalTimeMinutes < nowMinutes || arrivalTimeMinutes > maxTimeMinutes) {
                    continue;
                }

                String tripId = parts[0];
                stopTimeList.add(new StopTime(tripId, arrivalTimeMinutes));
            }
            return stopTimeList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
