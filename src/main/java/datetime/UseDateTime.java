package datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class UseDateTime {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        System.out.println("today is " + today);
        LocalDate later = today.plusDays(14);
        System.out.println("modified today is " + later);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMMM / dd / yyyyyy");
        System.out.println("Formatted: " + fmt.format(later));

//        ZoneId.getAvailableZoneIds().stream().forEach(System.out::println);
        ZoneId monaco = ZoneId.of("Europe/Monaco");
        ZonedDateTime nov6Midnight = ZonedDateTime.of(
                later, LocalTime.MIDNIGHT, monaco);
        System.out.println(nov6Midnight);
        ZonedDateTime landing = nov6Midnight.plus(9, ChronoUnit.HOURS);
        landing = landing.withZoneSameInstant(ZoneId.of("US/Eastern"));
        System.out.println("Landing at " + landing);

        Duration sixHoursFiveMinutes = Duration.ofHours(6).plusMinutes(5);
        System.out.println(sixHoursFiveMinutes);

        landing = landing.plus(sixHoursFiveMinutes);
        System.out.println("and later is " + landing);

        Period threeYears16Months400Days = Period.of(3, 16, 400);
        System.out.println(threeYears16Months400Days);
        System.out.println(threeYears16Months400Days.normalized());

        Period threeSixtyFive = Period.ofDays(365);
        LocalDate janOne1900 = LocalDate.of(1900, 1, 1);
        LocalDate janOne2000 = LocalDate.of(2000, 1, 1);

        System.out.println("one year from 1900 " + janOne1900.plus(threeSixtyFive));
        System.out.println("one year from 2000 " + janOne2000.plus(threeSixtyFive));

        TemporalAdjuster nextFriday = TemporalAdjusters.next(DayOfWeek.FRIDAY);
        System.out.println("Next Friday is " + today.with(nextFriday));
    }
}
