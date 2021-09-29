package com.example.wpws;

public class ForecastItem {

    private String fcName;
    private String fcLocation;
    private String fcDesc;
    private String fcTemp;
    private String fcRh;
    private String fcWspeed;
    private String fcPop;
    private String fcRealFeel;
    private String fcUv;

    ForecastItem(String fcName, String fcLocation, String fcDesc, String fcTemp, String fcRh, String fcWspeed, String fcPop)
    {
        this.fcName = fcName;
        this.fcLocation = fcLocation;
        this.fcDesc = fcDesc;
        this.fcTemp = "Temperature: " + fcTemp + " C";
        this.fcRh = "RH: " + fcRh + "%";
        this.fcWspeed = "Wind speed: " + fcWspeed + " m/s";
        this.fcPop = "Chance of Rain: " + fcPop + "%";
        this.fcRealFeel = "Real feel: " + fcRealFeel + " C";
        this.fcUv = "UV index: " + fcUv;
    }

    ForecastItem(Forecast fc)
    {
        CurrentWeather cw = fc.getCurrentWeather();

        fcName = fc.getName();
        fcLocation = "(" + fc.getCityName() + ", " + fc.getCountryName() + ")";
        fcDesc = cw.getDesc();
        fcTemp = "Temperature: " + cw.getTemp() + " C";
        fcRh = "RH: " + cw.getRh() + "%";
        fcWspeed = "Wind sppeed: " + cw.getWspeed() + " m/s";
        fcPop = "Chance of Rain: " + fc.getDays().get(0).getPop() + "%";
        fcRealFeel = "Real feel: " + cw.getRealFeel() + " C";
        fcUv = "UV index: " + cw.getUv();
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

    public String getFcRealFeel() {
        return fcRealFeel;
    }

    public String getFcUv() {
        return fcUv;
    }
}
