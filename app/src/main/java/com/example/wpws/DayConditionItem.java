package com.example.wpws;

public class DayConditionItem {

    private String DayNumber;
    private String temperature;
    private String rh;
    private String pressure;
    private String minTemp;
    private String maxTemp;
    private String snow;
    private String snowDepth;
    private String clouds;
    private String windSpeed;
    private String precipitation;
    private String rain;

    public DayConditionItem(String DayNumber, String temperature, String rh, String pressure, String minTemp,
                            String maxTemp, String snow, String snowDepth, String clouds, String windSpeed,
                            String precipitation, String rain)
    {
        this.DayNumber = DayNumber;
        this.temperature = temperature;
        this.rh = rh;
        this.pressure = pressure;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.snow = snow;
        this.snowDepth = snowDepth;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.precipitation = precipitation;
        this.rain = rain;
    }

    public DayConditionItem(DayConditions day, int dayNr)
    {
        setDefault();

        DayNumber = "Day " + dayNr;
        for(Condition condition : day.getConditions())
        {
            String message = "";
            switch(condition.getConditionMode())
            {
                case LOWER:
                    message = " < " + condition.getValue();
                    break;
                case LOWER_EQ:
                    message = " <= " + condition.getValue();
                    break;
                case EQUAL:
                    message = " = " + condition.getValue();
                    break;
                case HIGHER:
                    message = " > " + condition.getValue();
                    break;
                case HIGHER_EQ:
                    message = " >= " + condition.getValue();
                    break;
                case DONT_CARE:
                    message = " aici era problema";
                default:
                    message = " doesn't matter.";
            }
            switch(condition.getConditionType())
            {
                case TEMP:
                    temperature = "Temp" + message + "C";
                    break;
                case RH:
                    rh = "RH" + message + "%";
                    break;
                case PRES:
                    pressure = "Pressure" + message + "mb";
                    break;
                case MIN_TEMP:
                    minTemp = "Min temp" + message + "C";
                    break;
                case MAX_TEMP:
                    maxTemp = "Max temp" + message + "C";
                    break;
                case SNOW:
                    snow = "Snow" + message + "mm";
                    break;
                case SNOW_DEPTH:
                    snowDepth = "Snow depth" + message + "mm";
                    break;
                case CLOUDS:
                    clouds = "Clouds" + message + "%";
                    break;
                case WND_SPEED:
                    windSpeed = "Wind speed" + message + "m/s";
                    break;
                case PRECIP:
                    precipitation = "Precipitation" + message + "mm";
                    break;
                case POP:
                    rain = "Chance of rain" + message + "%";
                    break;
                default:
                    break;
            }
        }

    }

    private void setDefault()
    {
        DayNumber = "Day 0";
        temperature = "Temp doesn't matter";
        rh = "RH doesn't matter";
        pressure = "Pressure doesn't matter";
        minTemp = "Min temp doesn't matter";
        maxTemp = "Max temp doesn't matter";
        snow = "Snow doesn't matter";
        snowDepth = "Snow depth doesn't matter";
        clouds = "Clouds doesn't matter";
        windSpeed = "Wind speed doesn't matter";
        precipitation = "Precipitation doesn't matter";
        rain = "Chance of rain doesn't matter";
    }

    public String getDayNumber() {
        return DayNumber;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getRh() {
        return rh;
    }

    public String getPressure() {
        return pressure;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public String getSnow() {
        return snow;
    }

    public String getSnowDepth() {
        return snowDepth;
    }

    public String getClouds() {
        return clouds;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public String getRain() {
        return rain;
    }
}
