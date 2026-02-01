package test;

import main.BusTripsService;
import main.model.Arrival;
import main.model.RouteArrivals;
import main.model.StopTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BusTripsServiceTest {

    private BusTripsService service;

    @BeforeEach
    void setUp() {
        //paths not important for unit test
        service = new BusTripsService("", "", "");
    }

    @Test
    void groupStopTimesByGroupNameTest() {
        List<StopTime> stopTimes = List.of(
                new StopTime("T1", 500),
                new StopTime("T2", 520),
                new StopTime("T3", 510)
        );

        Map<String, Integer> tripToRouteId = Map.of(
                "T1", 1,
                "T2", 1,
                "T3", 2
        );

        Map<Integer, String> routeIdToName = Map.of(
                1, "10A",
                2, "20B"
        );

        Map<String, List<Arrival>> result =
                service.groupStopTimesByGroupName(stopTimes, tripToRouteId, routeIdToName);

        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.containsKey("10A"));
        Assertions.assertTrue(result.containsKey("20B"));

        Assertions.assertEquals(2, result.get("10A").size());
        Assertions.assertEquals(1, result.get("20B").size());
    }


    @Test
    void buildRouteArrivalsTest() {
        Map<String, List<Arrival>> input = new HashMap<>();

        input.put("10A", List.of(
                new Arrival(600),
                new Arrival(550),
                new Arrival(580)
        ));

        List<RouteArrivals> result = service.buildRouteArrivals(input, 2);

        Assertions.assertEquals(1, result.size());

        RouteArrivals routeArrivals = result.getFirst();
        List<Arrival> arrivals = routeArrivals.getArrivals();

        Assertions.assertEquals(2, arrivals.size());
        Assertions.assertEquals(550, arrivals.get(0).getArrivalTime());
        Assertions.assertEquals(580, arrivals.get(1).getArrivalTime());
    }
}
