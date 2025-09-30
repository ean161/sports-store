package fcc.sportsstore.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Time {

    final private ZoneId timeZone;

    public Time() {
        this.timeZone = ZoneId.of("Asia/Ho_Chi_Minh");
    }

    public ZonedDateTime getNow() {
        return Instant.now().atZone(timeZone);
    }

    public Long getCurrentTimestamp() {
        return getNow().toEpochSecond();
    }
}
