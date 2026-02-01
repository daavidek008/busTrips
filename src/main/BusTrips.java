package main;

import main.enums.TimeDisplayEnum;
import main.model.Arrival;
import main.model.RouteArrivals;
import main.util.TimeDisplayFormatter;

import java.time.LocalTime;
import java.util.List;

public class BusTrips {
    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Usage: busTrips <station_id> <num_buses_per_line> <relative|absolute>");
        }

        int stopId = parseInt(args[0]);
        int maxBuses = parseInt(args[1]);
        main.enums.TimeDisplayEnum displayEnum = main.enums.TimeDisplayEnum.from(args[2]);

        final String stopTimesPath = "gtfs/stop_times.txt";
        final String tripsPath = "gtfs/trips.txt";
        final String routesPath = "gtfs/routes.txt";

//        int stopId = 2;
//        int maxBuses = 10;
//        TimeDisplayEnum displayEnum = TimeDisplayEnum.ABSOLUTE;

        int nowMinutes = LocalTime.now().toSecondOfDay() / 60;

        BusTripsService busTripsService = new BusTripsService(stopTimesPath, tripsPath, routesPath);
        List<RouteArrivals> routeArrivalsList = busTripsService.getArrivalsPerRoute(stopId, maxBuses, nowMinutes);

        printRouteArrivals(routeArrivalsList, nowMinutes, displayEnum);
    }

    public static void printRouteArrivals(List<RouteArrivals> routes, int nowMinutes, TimeDisplayEnum timeDisplayEnum) {
        for (RouteArrivals route : routes) {
            System.out.println("Route " + route.getRouteName() + ":");
            for (Arrival arrival : route.getArrivals()) {
                String formatted = TimeDisplayFormatter.format(arrival.getArrivalTime(), nowMinutes, timeDisplayEnum);
                System.out.println("  " + formatted);
            }
        }
    }

    private static int parseInt(String value) {
        try {
            int parsed = Integer.parseInt(value);
            if (parsed <= 0) {
                throw new IllegalArgumentException("Value must be > 0!");
            }
            return parsed;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Value must be valid integer!");
        }
    }
}