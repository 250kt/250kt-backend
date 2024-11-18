package fr.gofly.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherHelper {

    public boolean isMetarExpired(LocalDateTime metarPublishedAt) {
        LocalDateTime now = LocalDateTime.now();
        return metarPublishedAt.plusMinutes(30).isBefore(now);
    }

    public boolean isTafExpired(LocalDateTime tafPublishedAt) {
        LocalDateTime now = LocalDateTime.now();
        return tafPublishedAt.plusMinutes(60).isBefore(now);
    }

    public LocalDateTime parsePublishedAt(String publishedAt) {
        int day = Integer.parseInt(publishedAt.substring(0, 2));
        int hour = Integer.parseInt(publishedAt.substring(2, 4));
        int minute = Integer.parseInt(publishedAt.substring(4, 6));

        LocalDateTime now = LocalDateTime.now();

        return now.withDayOfMonth(day).withHour(hour).withMinute(minute).withSecond(0).withNano(0);
    }

    public String removeTafIfIsPresent(String data) {
        if (data.contains("TAF")) {
            return data.substring(4);
        }
        return data;
    }
}
