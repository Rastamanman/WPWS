package com.example.wpws;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CurrentWeather {

    private float temp; //temperature (C)
    private float realFeel; //real feel temp (C)
    private float rh; //relative humidity (%)
    private String desc; //current weather description
    private float wspeed; //wind speed(m/s)
    private float precip; //precipitation rate(mm/hr)
    private float pres; //pressure (mb)
    private float uv; //uv index (0-11+)

    CurrentWeather()
    {
        temp = 0;
        realFeel = 0;
        rh = 0;
        desc = "";
        wspeed = 0;
        precip = 0;
        pres = 0;
        uv = 0;
    }

    CurrentWeather(JSONObject obj) throws JSONException {
        temp = Float.parseFloat(obj.getString("temp"));
        realFeel = Float.parseFloat(obj.getString("app_temp"));
        rh = Float.parseFloat(obj.getString("rh"));
        wspeed = Float.parseFloat(obj.getString("wind_spd"));
        precip = Float.parseFloat(obj.getString("precip"));
        pres = Float.parseFloat(obj.getString("pres"));
        uv = Float.parseFloat(obj.getString("uv"));
        desc = obj.getJSONObject("weather").getString("description");
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getWspeed() {
        return wspeed;
    }

    public void setWspeed(float wspeed) {
        this.wspeed = wspeed;
    }

    public float getPrecip() {
        return precip;
    }

    public void setPrecip(float precip) {
        this.precip = precip;
    }

    public float getPres() {
        return pres;
    }

    public void setPres(float pres) {
        this.pres = pres;
    }

    public float getUv() {
        return uv;
    }

    public void setUv(float uv) {
        this.uv = uv;
    }
}
