package com.example.wpws;

public class ForecastItem {

    private String fcName;
    private String fcLocation;
    private String fcDesc;
    private String fcTemp;
    private String fcRh;
    private String fcWspeed;
    private String fcPop;

    ForecastItem(String fcName, String fcLocation, String fcDesc, String fcTemp, String fcRh, String fcWspeed, String fcPop)
    {
        this.fcName = fcName;
        this.fcLocation = fcLocation;
        this.fcDesc = fcDesc;
        this.fcTemp = "Temperature: " + fcTemp + " C";
        this.fcRh = "RH: " + fcRh + "%";
        this.fcWspeed = "Wind speed: " + fcWspeed + " m/s";
        this.fcPop = "Chance of Rain: " + fcPop + "%";
    }

    ForecastItem(Forecast fc)
    {
        Day azi = fc.getDays().get(1);

        fcName = fc.getLocation().getName();
        fcLocation = "(" + fc.getCityName() + ", " + fc.getCountryName() + ")";
        fcDesc = azi.getWeather_desc();
        fcTemp = "Temperature: " + azi.getTemp() + " C";
        fcRh = "RH: " + azi.getRh() + "%";
        fcWspeed = "Wind sppeed: " + azi.getWind_spd() + " m/s";
        fcPop = "Chance of Rain: " + azi.getPop() + "%";
    }

    public String getFcName() {
        return fcName;
    }

    public String getFcLocation() {
        return fcLocation;
    }

    public String getFcDesc() {
        return fcDesc;
    }

    public String getFcTemp() {
        return fcTemp;
    }

    public String getFcRh() {
        return fcRh;
    }

    public String getFcWspeed() {
        return fcWspeed;
    }

    public String getFcPop() {
        return fcPop;
    }
}
