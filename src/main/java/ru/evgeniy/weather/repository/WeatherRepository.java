package ru.evgeniy.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.evgeniy.weather.model.Weather;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

}
