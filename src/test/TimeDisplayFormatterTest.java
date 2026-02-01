package test;

import main.enums.TimeDisplayEnum;
import main.util.TimeDisplayFormatter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeDisplayFormatterTest {

    @Test
    public void formatAbsoluteTimeTest() {
        int arrivalTimeMinutes = 90;
        int nowMinutes = 0; // irrelevant for ABSOLUTE

        String result = TimeDisplayFormatter.format(
                arrivalTimeMinutes,
                nowMinutes,
                TimeDisplayEnum.ABSOLUTE
        );

        assertEquals("01:30", result);
    }

    @Test
    public void formatRelativeTimeTest() {
        int arrivalTimeMinutes = 120;
        int nowMinutes = 90;

        String result = TimeDisplayFormatter.format(
                arrivalTimeMinutes,
                nowMinutes,
                TimeDisplayEnum.RELATIVE
        );

        assertEquals("30 min", result);
    }
}
