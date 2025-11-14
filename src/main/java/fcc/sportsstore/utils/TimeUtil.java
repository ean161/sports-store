package fcc.sportsstore.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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

    public Long getTimestamp(String date) {
        return LocalDateTime.parse(date)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli() / 1000;
    }
}