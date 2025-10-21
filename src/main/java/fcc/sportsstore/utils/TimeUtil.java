package fcc.sportsstore.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeUtil {

    final private ZoneId timeZone;

    public TimeUtil() {
        this.timeZone = ZoneId.of("Asia/Ho_Chi_Minh");
    }

    public ZonedDateTime getNow() {
        return Instant.now().atZone(timeZone);
    }

    public Long getCurrentTimestamp() {
        return getNow().toEpochSecond();
    }
}
