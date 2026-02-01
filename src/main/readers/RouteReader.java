package main.readers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Reads route data from the routes.txt file and maps route IDs to route names.
 */
public class RouteReader {

    /**
     * Parses route names for the given set of route IDs.
     *
     * @param setRouteIds a set of route IDs to look up
     * @param routesPath path to routes text file
     * @return a map where the key is a route ID and the value is the route name;
     */
    public Map<Integer, String> parseRouteNameByRouteId(Set<Integer> setRouteIds, String routesPath) {
        Map<Integer, String> routeIdToRouteNameMap = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(routesPath)));
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] parts = line.split(",");
                int lineRouteId = Integer.parseInt(parts[0]);

                if (setRouteIds.contains(lineRouteId)) {
                    String routeName = parts[2];
                    routeIdToRouteNameMap.put(lineRouteId, routeName);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return routeIdToRouteNameMap;
    }
}
