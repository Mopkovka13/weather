package ru.evgeniy.weather.service;

import ru.evgeniy.weather.model.Weather;

import java.util.List;

public interface WeatherService {
    Weather getCurrentWeather(Double latitude, Double longitude);
    List<Weather> getWeathers();
}
