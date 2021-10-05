package com.example.wpws;

public class ForecastItem {

    private String forecastName;
    private String forecastLocation;
    private String forecastDescription;
    private String forecastTemperature;
    private String forecastRh;
    private String forecastWindSpeed;
    private String forecastRain;
    private String forecastRealFeel;
    private String forecastUv;

    ForecastItem(String forecastName, String forecastLocation, String forecastDescription,
                 String forecastTemperature, String forecastRh, String forecastWindSpeed,
                 String forecastRain, String forecastRealFeel, String forecastUv)
    {
        this.forecastName = forecastName;
        this.forecastLocation = forecastLocation;
        this.forecastDescription = forecastDescription;
        this.forecastTemperature = "Temperature: " + forecastTemperature + " C";
        this.forecastRh = "RH: " + forecastRh + "%";
        this.forecastWindSpeed = "Wind speed: " + forecastWindSpeed + " m/s";
        this.forecastRain = "Chance of Rain: " + forecastRain + "%";
        this.forecastRealFeel = "Real feel: " + forecastRealFeel + " C";
        this.forecastUv = "UV index: " + forecastUv;
    }

    ForecastItem(Forecast forecast)
    {
        CurrentWeather currentWeather = forecast.getCurrentWeather();

        forecastName = forecast.getName();
        forecastLocation = "(" + forecast.getCityName() + ", " + forecast.getCountryName() + ")";
        forecastDescription = currentWeather.getDescription();
        forecastTemperature = "Temperature: " + currentWeather.getTemperature() + " C";
        forecastRh = "RH: " + currentWeather.getRh() + "%";
        forecastWindSpeed = "Wind sppeed: " + currentWeather.getWindSpeed() + " m/s";
        forecastRain = "Chance of Rain: " + forecast.getDays().get(0).getRain() + "%";
        forecastRealFeel = "Real feel: " + currentWeather.getRealFeel() + " C";
        forecastUv = "UV index: " + currentWeather.getUv();
    }

    public String getForecastName() {
        return forecastName;
    }

    public String getForecastLocation() {
        return forecastLocation;
    }

    public String getForecastDescription() {
        return forecastDescription;
    }

    public String getForecastTemperature() {
        return forecastTemperature;
    }

    public String getForecastRh() {
        return forecastRh;
    }

    public String getForecastWindSpeed() {
        return forecastWindSpeed;
    }

    public String getForecastRain() {
        return forecastRain;
    }

    public String getForecastRealFeel() {
        return forecastRealFeel;
    }

    public String getForecastUv() {
        return forecastUv;
    }
}
