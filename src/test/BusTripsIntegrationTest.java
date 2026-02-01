package test;

import main.BusTripsService;
import main.model.Arrival;
import main.model.RouteArrivals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

class BusTripsIntegrationTest {

    private BusTripsService service;

    @BeforeEach
    void setUp() {
        //paths not important for unit test
        service = new BusTripsService(
                "src/test/resources/gtfs/stop_times.txt",
                "src/test/resources/gtfs/trips.txt",
                "src/test/resources/gtfs/routes.txt"
        );
    }

    @Test
    void findsUpcomingArrivalsForStop() {

        LocalTime now = LocalTime.of(10, 0);
        int nowMinutes = now.toSecondOfDay() / 60;

        List<RouteArrivals> routeArrivalsList = service.getArrivalsPerRoute(10, 2, nowMinutes);

        Assertions.assertEquals(5, routeArrivalsList.size());

        RouteArrivals route5 = routeArrivalsList.get(4);
        Assertions.assertEquals("107", route5.getRouteName());
        Assertions.assertEquals(2, route5.getArrivals().size());

        Arrival first = route5.getArrivals().getFirst();
        Assertions.assertEquals(600, first.getArrivalTime());
    }
}
