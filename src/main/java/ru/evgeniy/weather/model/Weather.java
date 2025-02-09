package ru.evgeniy.weather.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Table(name = "weather")
@AllArgsConstructor
@NoArgsConstructor
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WeatherType type;

    private Double latitude;

    private Double longitude;

    private Double temperature;

    private Double feelsLike;

    private String description;

    private LocalDateTime timestamp;
}
