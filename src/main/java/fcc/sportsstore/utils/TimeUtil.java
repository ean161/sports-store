package fcc.sportsstore.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeUtil {

    final private ZoneId timeZone;

    /**
     * Constructor
     */
    public TimeUtil() {
        this.timeZone = ZoneId.of("Asia/Ho_Chi_Minh");
    }

    /**
     * Get now date time
     * @return Current date time
     */
    public ZonedDateTime getNow() {
        return Instant.now().atZone(timeZone);
    }

    /**
     * Get current timestamp
     * @return Current timestamp
     */
    public Long getCurrentTimestamp() {
        return getNow().toEpochSecond();
    }
}
