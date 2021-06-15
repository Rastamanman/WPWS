package com.example.wpws;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Forecast {

    private List<Day> zile;
    private Location location;

    public Forecast()
    {
        location = new Location();
        zile = new ArrayList<>();
    }

    public Forecast(Location loc)
    {
        location = loc;
        zile= new ArrayList<>();
    }

    public void addDay(JSONObject obj)
    {
        zile.add(new Day(obj));
    }

    public float getLatitude()
    {
        return location.getLatitude();
    }

    public void setLatitude(float latToSet)
    {
        location.setLatitude(latToSet);
    }

    public float getLongitude()
    {
        return location.getLongitude();
    }

    public void setLongitude(float longToSet)
    {
        location.setLongitude(longToSet);
    }

    public String getCityName()
    {
        return location.getCity_name();
    }

    public void setCityName(String nameToSet)
    {
        location.setCity_name(nameToSet);
    }

    public String getCountryName()
    {
        return location.getCountry_name();
    }

    public void setCountryName(String nameToSet)
    {
        location.setCountry_name(nameToSet);
    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location locToSet)
    {
        location = locToSet;
    }

    public void clearForecast()
    {
        zile.clear();
    }

    public List<Day> getDays() { return zile; }

    public void setDays(List<Day> zileToSet) { zile = zileToSet; }

}
