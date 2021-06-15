package com.example.wpws;


public class Location {

    private float latitude;
    private float longitude;
    private String city_name;
    private String country_name;
    private String name;


    public Location()
    {
        latitude = 0;
        longitude = 0;
        city_name = "";
        country_name = "";
        name = "";
    }

    public Location(float lat, float lon, String cityName, String countryName, String name)
    {
        latitude = lat;
        longitude = lon;
        city_name = cityName;
        country_name = countryName;
        this.name = name;
    }

    public Location(float lat, float lon, String cityName, String countryName)
    {
        latitude = lat;
        longitude = lon;
        city_name = cityName;
        country_name = countryName;
        name = cityName;
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

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getName() { return name; }

    public void setName(String nameToSet) { name = nameToSet; }

}

