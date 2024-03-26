package org.example.universitymanagementsystem.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateUtil {
    private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static final ZoneId bakuZone = ZoneId.of("Asia/Baku");

    public static String toDateTimeString(Instant instant) {
        if (instant == null) {
            return null;
        }
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, bakuZone);
        Date date = Date.from(zonedDateTime.toInstant());
        return datetimeFormat.format(date);
    }
}
