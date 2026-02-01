package main.enums;

/**
 * To avoid string comparasion
 */
public enum TimeDisplayEnum {
    RELATIVE,
    ABSOLUTE;

    public static TimeDisplayEnum from(String value) {
        return switch (value.toLowerCase()) {
            case "relative" -> RELATIVE;
            case "absolute" -> ABSOLUTE;
            default -> throw new IllegalArgumentException(
                    "Invalid time format: " + value + " (expected 'relative' or 'absolute')"
            );
        };
    }
}
