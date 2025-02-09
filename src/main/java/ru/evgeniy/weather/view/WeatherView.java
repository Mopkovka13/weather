package ru.evgeniy.weather.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.server.StreamResource;
import org.springframework.core.io.ClassPathResource;
import ru.evgeniy.weather.model.Weather;
import ru.evgeniy.weather.model.WeatherType;
import ru.evgeniy.weather.service.WeatherService;
import ru.evgeniy.weather.util.MyApplicationContextProvider;

import java.util.HashMap;
import java.util.Map;

@CssImport("themes/my-theme/component/weather.css")
public class WeatherView extends VerticalLayout {
    WeatherService weatherService = MyApplicationContextProvider.getApplicationContext().getBean(WeatherService.class);

    Map<WeatherType, String> weatherTypeImages;

    private NumberField latitudeField;
    private NumberField longitudeField;
    private Button checkWeatherButton;
    private Button checkHistoryWeatherButton;
    private Div weatherText;
    private Image weatherImage;

    public WeatherView() {
        setClassName("weather-form");

        initWeatherImages();
        initComponent();

        HorizontalLayout fieldLayout = new HorizontalLayout();
        fieldLayout.add(latitudeField, longitudeField);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        buttonLayout.add(checkWeatherButton, checkHistoryWeatherButton);

        add(weatherText, weatherImage, fieldLayout, buttonLayout);
    }

    private void initComponent() {
        weatherText = new Div();
        weatherImage = new Image();
        weatherImage.setWidth("80%");
        weatherImage.setClassName("centered-image");

        latitudeField = new NumberField("Широта");
        latitudeField.setMin(-90);
        latitudeField.setMax(90);
        latitudeField.setPlaceholder("от -90 до 90");

        longitudeField = new NumberField("Долгота");
        longitudeField.setMin(-180);
        longitudeField.setMax(180);
        longitudeField.setPlaceholder("от -180 до 180");

        checkWeatherButton = new Button("Показать погоду!");
        checkWeatherButton.addClickListener(x -> {
            Double latitude = latitudeField.getValue();
            Double longitude = longitudeField.getValue();

            if (latitude == null || longitude == null) {
                Notification.show("Введите координаты!");
            }

            if (!isValidLatitude(latitude) || !isValidLongitude(longitude)) {
                Notification.show("Координаты вне допустимого диапазона!");
            }

            Weather weather = weatherService.getCurrentWeather(latitude, longitude);
            String weatherInfo = String.format("%s<br>%d градусов, ощущается как %d",
                    weather.getDescription(),
                    (int) weather.getTemperature().doubleValue(),
                    (int) weather.getFeelsLike().doubleValue());
            weatherText.getElement().setProperty("innerHTML", weatherInfo);

            // По типу погоды берем изображение из мапы
            StreamResource imageResource = new StreamResource("weatherImage", () ->
                    getClass().getResourceAsStream(weatherTypeImages.get(weather.getType()))
            );
            weatherImage.setSrc(imageResource);
        });

        checkHistoryWeatherButton = new Button("Показать историю");
        checkHistoryWeatherButton.addClickListener(x -> {
           WeatherHistoryDialog dialog = new WeatherHistoryDialog();

           dialog.open();
        });
    }

    private void initWeatherImages() {
        weatherTypeImages = new HashMap<>();
        weatherTypeImages.put(WeatherType.CLEAR, "/img/CLEAR.png");
        weatherTypeImages.put(WeatherType.CLOUDS, "/img/CLOUD.png");
        weatherTypeImages.put(WeatherType.RAIN, "/img/RAIN.png");
        weatherTypeImages.put(WeatherType.DRIZZLE, "/img/DRIZZLE.png");
        weatherTypeImages.put(WeatherType.THUNDERSTORM, "/img/THUNDERSTORM.png");
        weatherTypeImages.put(WeatherType.SNOW, "/img/SNOW.png");
        weatherTypeImages.put(WeatherType.MIST, "/img/MIST.png");
        weatherTypeImages.put(WeatherType.HAZE, "/img/HAZE.png");
        weatherTypeImages.put(WeatherType.DUST, "/img/DUST.png");
        weatherTypeImages.put(WeatherType.FOG, "/img/HAZE.png");
        weatherTypeImages.put(WeatherType.ASH, "/img/HAZE.png");
        weatherTypeImages.put(WeatherType.SQUALL, "/img/SQUALL.png");
        weatherTypeImages.put(WeatherType.TORNADO, "/img/TORNADO.png");
    }

    private boolean isValidLatitude(Double latitude) {
        return latitude >= -90 && latitude <= 90;
    }

    private boolean isValidLongitude(Double longitude) {
        return longitude >= -180 && longitude <= 180;
    }
}
