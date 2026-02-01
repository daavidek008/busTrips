package main.model;

public class StopTime {
    private final String tripId;
    private final int arrivalTimeMinutes;

    public StopTime(String tripId, int arrivalTimeMinutes) {
        this.tripId = tripId;
        this.arrivalTimeMinutes = arrivalTimeMinutes;
    }

    public String getTripId() {
        return tripId;
    }

    public int getArrivalTimeMinutes() {
        return arrivalTimeMinutes;
    }

}

