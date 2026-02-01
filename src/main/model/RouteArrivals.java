package main.model;

import java.util.List;

public class RouteArrivals {
    private final String routeName;
    private final List<Arrival> arrivals;

    public RouteArrivals(String routeName, List<Arrival> arrivals) {
        this.routeName = routeName;
        this.arrivals = arrivals;
    }

    public String getRouteName() {
        return routeName;
    }

    public List<Arrival> getArrivals() {
        return arrivals;
    }

}
