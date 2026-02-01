package main;

import main.model.Arrival;
import main.model.RouteArrivals;
import main.model.StopTime;
import main.readers.RouteReader;
import main.readers.StopTimeReader;
import main.readers.TripsReader;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service responsible for retrieving, grouping, and displaying upcoming bus arrivals
 * for a given stop.
 */
public class BusTripsService {

    private final String stopTimesPath;
    private final String tripsPath;
    private final String routesPath;


    public BusTripsService(String stopTimesPath, String tripsPath, String routesPath) {
        this.stopTimesPath = stopTimesPath;
        this.tripsPath = tripsPath;
        this.routesPath = routesPath;
    }

    public List<RouteArrivals> getArrivalsPerRoute(int stopId, int maxBuses, int nowMinutes) {
        
        StopTimeReader stopTimeReader = new StopTimeReader();
        List<StopTime> stopTimeList = stopTimeReader.parseStopTimeByStopId(stopId, nowMinutes, stopTimesPath);

        Set<String> setTripIds = stopTimeList.stream().map(StopTime::getTripId).collect(Collectors.toSet());

        TripsReader tripsReader = new TripsReader();
        Map<String, Integer> tripIdToRouteIdMap = tripsReader.parseRoutesByTripId(setTripIds, tripsPath);

        Set<Integer> routeIds = new HashSet<>(tripIdToRouteIdMap.values());

        RouteReader routeReader = new RouteReader();
        Map<Integer, String> routeIdToRouteNameMap = routeReader.parseRouteNameByRouteId(routeIds, routesPath);

        Map<String, List<Arrival>> routeNameToArrivalMap = groupStopTimesByGroupName(stopTimeList, tripIdToRouteIdMap, routeIdToRouteNameMap);

        return buildRouteArrivals(routeNameToArrivalMap, maxBuses);
    }

    public Map<String, List<Arrival>> groupStopTimesByGroupName(
            List<StopTime> stopTimeList,
            Map<String, Integer> tripIdToRouteIdMap,
            Map<Integer, String> routeIdToRouteNameMap) {

        Map<String, List<Arrival>> routeNameToArrivalMap = new HashMap<>();

        for (StopTime stopTime : stopTimeList) {
            int routeId = tripIdToRouteIdMap.get(stopTime.getTripId());
            String routeName = routeIdToRouteNameMap.get(routeId);

            List<Arrival> list = routeNameToArrivalMap.computeIfAbsent(routeName, k -> new ArrayList<>());
            list.add(new Arrival(stopTime.getArrivalTimeMinutes()));
        }
        return routeNameToArrivalMap;
    }

    public List<RouteArrivals> buildRouteArrivals(Map<String, List<Arrival>> routeNameToArrivalMap, int maxBuses) {
        List<RouteArrivals> routeArrivalsList = new ArrayList<>();
        for (Map.Entry<String, List<Arrival>> entry : routeNameToArrivalMap.entrySet()) {
            List<Arrival> arrivals = new ArrayList<>(entry.getValue());
            arrivals.sort(Comparator.comparingInt(Arrival::getArrivalTime));

            if (arrivals.size() > maxBuses) {
                arrivals = arrivals.subList(0, maxBuses);
            }

            routeArrivalsList.add(new RouteArrivals(entry.getKey(), arrivals));
        }
        return routeArrivalsList;
    }


}
