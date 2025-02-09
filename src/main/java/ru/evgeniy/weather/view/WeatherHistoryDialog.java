package ru.evgeniy.weather.view;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import ru.evgeniy.weather.model.Weather;
import ru.evgeniy.weather.service.WeatherService;
import ru.evgeniy.weather.util.MyApplicationContextProvider;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WeatherHistoryDialog extends Dialog {
    WeatherService weatherService = MyApplicationContextProvider.getApplicationContext().getBean(WeatherService.class);

    public WeatherHistoryDialog() {
        setWidth("800");
        setHeight("500");

        List<Weather> weathers = weatherService.getWeathers();

        for (Weather weather : weathers) {
            ZoneId userTimeZone = ZoneId.systemDefault();
            ZonedDateTime userZonedDateTime = weather
                    .getTimestamp()
                    .atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(userTimeZone);

            LocalDateTime localTime = userZonedDateTime.toLocalDateTime();

            String formattedTime = localTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String result = String.format(
                    "%s | ширина: %.2f, долгота: %.2f | ответ: %s",
                    formattedTime,
                    weather.getLatitude(),
                    weather.getLongitude(),
                    weather.getDescription());

            add(new Div(result));
        }
    }
}
