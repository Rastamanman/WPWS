package com.example.wpws;


public class Location {

    private float latitude;
    private float longitude;
    private String cityName;
    private String countryName;
    private String name;


    public Location()
    {
        latitude = 0;
        longitude = 0;
        cityName = "";
        countryName = "";
        name = "";
    }

    public Location(float latitude, float longitude, String cityName, String countryName, String name)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityName = cityName;
        this.countryName = countryName;
        this.name = name;
    }

    public Location(float latitude, float longitude, String cityName, String countryName)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityName = cityName;
        this.countryName = countryName;
        name = cityName + ", " + countryName;
    }

    public Location(float latitude, float longitude, String name)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        cityName = "";
        countryName = "";
        this.name = name;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getName() { return name; }

    public void setName(String nameToSet) { name = nameToSet; }

}

