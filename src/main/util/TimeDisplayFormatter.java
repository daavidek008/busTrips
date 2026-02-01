package main.util;

import main.enums.TimeDisplayEnum;

import java.time.LocalTime;

public class TimeDisplayFormatter {

    public static String format(int arrivalTimeMinutes, int nowMinutes, TimeDisplayEnum timeDisplayEnum) {
        return switch (timeDisplayEnum) {
            case ABSOLUTE -> formatAbsolute(arrivalTimeMinutes);
            case RELATIVE -> formatRelative(arrivalTimeMinutes, nowMinutes);
        };
    }

    private static String formatAbsolute(int arrivalTimeMinutes) {
        return LocalTime.ofSecondOfDay(arrivalTimeMinutes* 60L).toString();
    }

    private static String formatRelative(int arrivalTimeMinutes, int nowMinutes){
        return arrivalTimeMinutes - nowMinutes + " min";
    }
}
