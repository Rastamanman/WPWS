package com.example.wpws;

import org.json.JSONException;
import org.json.JSONObject;

public class Day {

    private float rh; //average relative humidity (%)
    private float pressure; //average pressure (mb)
    private float minTemp; // minimum temperature (C)
    private float maxTemp; //maximum temperature (C)
    private float lowTemp; //low temperature (C)
    private float highTemp; //high temperature (C)
    private float temperature; //average temp (C)
    private float snow; //acumulated snowfall(mm)
    private float snowDepth; //(mm)
    private float clouds; //average total cloud coverage (%)
    private float windSpeed; //wind speed (m/s)
    private String windCardinalDirection; //wind direction abreviated
    private String validDate; //date the forecast is valid
    private String weatherIcon; //icon code
    private String weatherDescription; //desctiption
    private int weatherCode; //code
    private float precipitation; //acumulated liqud equivalent precipitation(mm)
    private float rain; //probability of precipitation(%)

    public Day()
    {
        rh = 0;
        pressure = 0;
        minTemp = 0;
        maxTemp = 0;
        lowTemp = 0;
        highTemp = 0;
        temperature = 0;
        snow = 0;
        snowDepth = 0;
        clouds = 0;
        windSpeed = 0;
        windCardinalDirection = "";
        validDate = "";
        weatherIcon = "";
        weatherDescription = "";
        weatherCode = 0;
        precipitation = 0;
        rain = 0;
    }

    public Day(JSONObject obj)
    {
        try {
            rh = Float.parseFloat(obj.getString("rh"));
            pressure = Float.parseFloat(obj.getString("pres"));
            minTemp = Float.parseFloat(obj.getString("min_temp"));
            maxTemp = Float.parseFloat(obj.getString("max_temp"));
            lowTemp = Float.parseFloat(obj.getString("low_temp"));
            highTemp = Float.parseFloat(obj.getString("high_temp"));
            temperature = Float.parseFloat(obj.getString("temp"));
            snow = Float.parseFloat(obj.getString("snow"));
            snowDepth = Float.parseFloat(obj.getString("snow_depth"));
            clouds = Float.parseFloat(obj.getString("clouds"));
            windSpeed = Float.parseFloat(obj.getString("wind_spd"));
            windCardinalDirection = obj.getString("wind_cdir");
            validDate = obj.getString("valid_date");
            weatherIcon = obj.getJSONObject("weather").getString("icon");
            weatherCode = Integer.parseInt(obj.getJSONObject("weather").getString("code"));
            weatherDescription = obj.getJSONObject("weather").getString("description");
            precipitation = Float.parseFloat(obj.getString("precip"));
            rain = Float.parseFloat(obj.getString("pop"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public float getRh() {
        return rh;
    }

    public void setRh(float rh) {
        this.rh = rh;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp;
    }

    public float getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(float lowTemp) {
        this.lowTemp = lowTemp;
    }

    public float getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(float highTemp) {
        this.highTemp = highTemp;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getSnow() {
        return snow;
    }

    public void setSnow(float snow) {
        this.snow = snow;
    }

    public float getSnowDepth() {
        return snowDepth;
    }

    public void setSnowDepth(float snowDepth) {
        this.snowDepth = snowDepth;
    }

    public float getClouds() {
        return clouds;
    }

    public void setClouds(float clouds) {
        this.clouds = clouds;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindCardinalDirection() {
        return windCardinalDirection;
    }

    public void setWindCardinalDirection(String windCardinalDirection) {
        this.windCardinalDirection = windCardinalDirection;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }

    public float getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(float precipitation) {
        this.precipitation = precipitation;
    }

    public float getRain() {
        return rain;
    }

    public void setRain(float rain) {
        this.rain = rain;
    }
}
