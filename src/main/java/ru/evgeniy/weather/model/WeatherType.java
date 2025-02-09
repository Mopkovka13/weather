package ru.evgeniy.weather.model;

public enum WeatherType {
    CLEAR("Clear"),
    CLOUDS("Clouds"),
    RAIN("Rain"),
    DRIZZLE("Drizzle"),
    THUNDERSTORM("Thunderstorm"),
    SNOW("Snow"),
    MIST("Mist"),
    HAZE("Haze"),
    DUST("Dust"),
    FOG("Fog"),
    ASH("Ash"),
    SQUALL("Squall"),
    TORNADO("Tornado");

    private final String main;

    WeatherType(String main) {
        this.main = main;
    }

    public String getMain() {
        return main;
    }

    public static WeatherType fromMain(String main) {
        for (WeatherType type : WeatherType.values()) {
            if (type.getMain().equalsIgnoreCase(main)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown weather type: " + main);
    }
}
