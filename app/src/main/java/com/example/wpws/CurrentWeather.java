package com.example.wpws;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrentWeather {

    private float temperature; //temperature (C)
    private float realFeel; //real feel temp (C)
    private float rh; //relative humidity (%)
    private String description; //current weather description
    private float windSpeed; //wind speed(m/s)
    private float precipitation; //precipitation rate(mm/hr)
    private float pressure; //pressure (mb)
    private float uv; //uv index (0-11+)

    public CurrentWeather()
    {
        temperature = 0;
        realFeel = 0;
        rh = 0;
        description = "";
        windSpeed = 0;
        precipitation = 0;
        pressure = 0;
        uv = 0;
    }

    public CurrentWeather(JSONObject obj) throws JSONException {
        temperature = Float.parseFloat(obj.getString("temp"));
        realFeel = Float.parseFloat(obj.getString("app_temp"));
        rh = Float.parseFloat(obj.getString("rh"));
        windSpeed = Float.parseFloat(obj.getString("wind_spd"));
        precipitation = Float.parseFloat(obj.getString("precip"));
        pressure = Float.parseFloat(obj.getString("pres"));
        uv = Float.parseFloat(obj.getString("uv"));
        description = obj.getJSONObject("weather").getString("description");
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getRealFeel() {
        return realFeel;
    }

    public void setRealFeel(float realFeel) {
        this.realFeel = realFeel;
    }

    public float getRh() {
        return rh;
    }

    public void setRh(float rh) {
        this.rh = rh;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(float precipitation) {
        this.precipitation = precipitation;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getUv() {
        return uv;
    }

    public void setUv(float uv) {
        this.uv = uv;
    }
}
