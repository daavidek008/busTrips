package main.readers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Reads trip data from the trips.txt file and maps trip IDs to route IDs.
 */
public class TripsReader {

    /**
     * Parses route IDs for the given set of trip IDs.
     *
     * @param setTripIds a set of trip IDs to look up
     * @param tripsPath path to trips text file
     * @return a map where the key is a trip ID and the value is the route ID;
     */
    public Map<String, Integer> parseRoutesByTripId(Set<String> setTripIds, String tripsPath) {
        Map<String, Integer> tripIdToRouteId = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(tripsPath)));
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] parts = line.split(",");
                String lineTripId = parts[2];
                if (setTripIds.contains(lineTripId)) {
                    int routeId = Integer.parseInt(parts[0]);
                    tripIdToRouteId.put(lineTripId, routeId);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tripIdToRouteId;
    }
}
