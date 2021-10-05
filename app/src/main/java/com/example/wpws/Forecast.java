package com.example.wpws;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Forecast  implements Runnable{

    private List<Day> days;
    private Location location;
    private CurrentWeather currentWeather;

    public Forecast()
    {
        location = new Location();
        days = new ArrayList<>();
        currentWeather = new CurrentWeather();
    }

    public Forecast(Location location)
    {
        this.location = location;
        days = new ArrayList<>();
        currentWeather = new CurrentWeather();
    }

    public void addDay(JSONObject obj)
    {
        days.add(new Day(obj));
    }

    public float getLatitude()
    {
        return location.getLatitude();
    }

    public void setLatitude(float latitude)
    {
        location.setLatitude(latitude);
    }

    public float getLongitude()
    {
        return location.getLongitude();
    }

    public void setLongitude(float longitude)
    {
        location.setLongitude(longitude);
    }

    public String getCityName()
    {
        return location.getCityName();
    }

    public void setCityName(String name)
    {
        location.setCityName(name);
    }

    public String getCountryName()
    {
        return location.getCountryName();
    }

    public void setCountryName(String name)
    {
        location.setCountryName(name);
    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location locationToSet)
    {
        location = locationToSet;
    }

    public void clearForecast()
    {
        days.clear();
    }

    public void clearCurrent() { currentWeather = new CurrentWeather(); }

    public List<Day> getDays() { return days; }

    public void setDays(List<Day> daysToSet) { days = daysToSet; }

    public void setCurrentWeather(JSONObject obj) throws JSONException {
        currentWeather = new CurrentWeather(obj);
    }

    public CurrentWeather getCurrentWeather()
    {
        return currentWeather;
    }

    public void updateForecastWeather()
    {
        JSONObject jsonWeather = null;
        try{
            jsonWeather = MainActivity.getWeatherJSON("" + getLatitude(), "" + getLongitude(), "FORECAST");
        } catch (Exception e)
        {
            Log.d("Error", "Cannot process JSON results", e);
        }
        try{
            if(jsonWeather != null)
            {
                clearForecast();
                for(int i = 0; i < jsonWeather.getJSONArray("data").length(); i++)
                {
                    addDay(jsonWeather.getJSONArray("data").getJSONObject(i));
                }
                setCityName(jsonWeather.getString("city_name"));
                setCountryName(jsonWeather.getString("country_code"));
                setLatitude(Float.parseFloat(jsonWeather.getString("lat")));
                setLongitude(Float.parseFloat(jsonWeather.getString("lon")));
            }
        } catch (Exception e){
            Log.d("Error", "Something went wrong", e);
        }
        if(days.isEmpty())
            updateForecastWeather();
    }

    public void updateCurrentWeather()
    {
        JSONObject jsonWeather = null;
        try{
            jsonWeather = MainActivity.getWeatherJSON("" + getLatitude(), "" + getLongitude(), "CURRENT");
        } catch (Exception e)
        {
            Log.d("Error", "Cannot process JSON results", e);
        }
        try{
            if(jsonWeather != null)
            {
                clearCurrent();
                setCurrentWeather(jsonWeather.getJSONArray("data").getJSONObject(0));
            }
        } catch (Exception e){
            Log.d("Error", "Something went wrong", e);
        }
    }

    public void updateForecast()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void updateForecastLinear()
    {
        updateForecastWeather();
        updateCurrentWeather();
    }

    //update Forecasts with threading
    @Override
    public void run()
    {
        updateForecastWeather();
        updateCurrentWeather();
    }

    public String getName()
    {
        return location.getName();
    }

}
