package main.util;

public class TimeParser {

    public static int toMinutes(String time) {
        // format: HH:MM:SS
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }
}
