package com.example.wpws;

import org.json.JSONException;
import org.json.JSONObject;

public class Day {

    private float rh; //average relative humidity (%)
    private float pres; //average pressure (mb)
    private float min_temp; // minimum temperature (C)
    private float max_temp; //maximum temperature (C)
    private float low_temp; //low temperature (C)
    private float high_temp; //high temperature (C)
    private float temp; //average temp (C)
    private float snow; //acumulated snowfall(mm)
    private float snow_depth; //(mm)
    private float clouds; //average total cloud coverage (%)
    private float wind_spd; //wind speed (m/s)
    private String wind_cdir; //wind direction abreviated
    private String valid_date; //date the forecast is valid
    private String weather_icon; //icon code
    private String weather_desc; //desctiption
    private int weather_code; //code
    private float precip; //acumulated liqud equivalent precipitation(mm)
    private float pop; //probability of precipitation(%)

    public Day()
    {
        rh = 0;
        pres = 0;
        min_temp = 0;
        max_temp = 0;
        low_temp = 0;
        high_temp = 0;
        temp = 0;
        snow = 0;
        snow_depth = 0;
        clouds = 0;
        wind_spd = 0;
        wind_cdir = "";
        valid_date = "";
        weather_icon = "";
        weather_desc = "";
        weather_code = 0;
        precip = 0;
        pop = 0;
    }

    public Day(JSONObject obj)
    {
        try {
            rh = Float.parseFloat(obj.getString("rh"));
            pres = Float.parseFloat(obj.getString("pres"));
            min_temp = Float.parseFloat(obj.getString("min_temp"));
            max_temp = Float.parseFloat(obj.getString("max_temp"));
            low_temp = Float.parseFloat(obj.getString("low_temp"));
            high_temp = Float.parseFloat(obj.getString("high_temp"));
            temp = Float.parseFloat(obj.getString("temp"));
            snow = Float.parseFloat(obj.getString("snow"));
            snow_depth = Float.parseFloat(obj.getString("snow_depth"));
            clouds = Float.parseFloat(obj.getString("clouds"));
            wind_spd = Float.parseFloat(obj.getString("wind_spd"));
            wind_cdir = obj.getString("wind_cdir");
            valid_date = obj.getString("valid_date");
            weather_icon = obj.getJSONObject("weather").getString("icon");
            weather_code = Integer.parseInt(obj.getJSONObject("weather").getString("code"));
            weather_desc = obj.getJSONObject("weather").getString("description");
            precip = Float.parseFloat(obj.getString("precip"));
            pop = Float.parseFloat(obj.getString("pop"));
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

    public float getPres() {
        return pres;
    }

    public void setPres(float pres) {
        this.pres = pres;
    }

    public float getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(float min_temp) {
        this.min_temp = min_temp;
    }

    public float getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(float max_temp) {
        this.max_temp = max_temp;
    }

    public float getLow_temp() {
        return low_temp;
    }

    public void setLow_temp(float low_temp) {
        this.low_temp = low_temp;
    }

    public float getHigh_temp() {
        return high_temp;
    }

    public void setHigh_temp(float high_temp) {
        this.high_temp = high_temp;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getSnow() {
        return snow;
    }

    public void setSnow(float snow) {
        this.snow = snow;
    }

    public float getSnow_depth() {
        return snow_depth;
    }

    public void setSnow_depth(float snow_depth) {
        this.snow_depth = snow_depth;
    }

    public float getClouds() {
        return clouds;
    }

    public void setClouds(float clouds) {
        this.clouds = clouds;
    }

    public float getWind_spd() {
        return wind_spd;
    }

    public void setWind_spd(float wind_spd) {
        this.wind_spd = wind_spd;
    }

    public String getWind_cdir() {
        return wind_cdir;
    }

    public void setWind_cdir(String wind_cdir) {
        this.wind_cdir = wind_cdir;
    }

    public String getValid_date() {
        return valid_date;
    }

    public void setValid_date(String valid_date) {
        this.valid_date = valid_date;
    }

    public String getWeather_icon() {
        return weather_icon;
    }

    public void setWeather_icon(String weather_icon) {
        this.weather_icon = weather_icon;
    }

    public String getWeather_desc() {
        return weather_desc;
    }

    public void setWeather_desc(String weather_desc) {
        this.weather_desc = weather_desc;
    }

    public int getWeather_code() {
        return weather_code;
    }

    public void setWeather_code(int weather_code) {
        this.weather_code = weather_code;
    }

    public float getPrecip() {
        return precip;
    }

    public void setPrecip(float precip) {
        this.precip = precip;
    }

    public float getPop() {
        return pop;
    }

    public void setPop(float pop) {
        this.pop = pop;
    }
}
