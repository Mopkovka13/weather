package ru.evgeniy.weather.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.evgeniy.weather.model.Weather;
import ru.evgeniy.weather.model.WeatherType;
import ru.evgeniy.weather.repository.WeatherRepository;
import ru.evgeniy.weather.service.WeatherService;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    @Value("${key.weather}")
    private String API_KEY;

    private final RestTemplate restTemplate;
    private final WeatherRepository weatherRepository;

    @Override
    @Transactional
    public Weather getCurrentWeather(Double latitude, Double longitude) {
        String url = "https://api.openweathermap.org/data/2.5/weather?" +
                "lat=" + latitude + "&" +
                "lon=" + longitude + "&" +
                "units=metric&" +
                "lang=ru&" +
                "appid=" + API_KEY;

        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(response);

            double temperature = rootNode.path("main").path("temp").asDouble();
            double feelsLike = rootNode.path("main").path("feels_like").asDouble();
            String description = rootNode.path("weather").get(0).path("description").asText();
            String type = rootNode.path("weather").get(0).path("main").asText();

            Weather weather = Weather.builder()
                    .longitude(longitude)
                    .latitude(latitude)
                    .temperature(temperature)
                    .feelsLike(feelsLike)
                    .description(description)
                    .type(WeatherType.fromMain(type))
                    .timestamp(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime())
                    .build();

            weatherRepository.save(weather);

            return weather;
        } catch (Exception e) { // TODO: Можно кастомные Exception сделать, и хендлер под них
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Weather> getWeathers() {
        return weatherRepository.findAll();
    }
}
