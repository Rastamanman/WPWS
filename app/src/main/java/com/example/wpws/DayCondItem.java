package com.example.wpws;

public class DayCondItem {

    private String dcDayNr;
    private String dcTemp;
    private String dcRh;
    private String dcPres;
    private String dcMinTemp;
    private String dcMaxTemp;
    private String dcSnow;
    private String dcSnowDepth;
    private String dcClouds;
    private String dcWSpeed;
    private String dcPrecip;
    private String dcPop;

    public DayCondItem(String dcDayNr, String dcTemp, String dcRh, String dcPres, String dcMinTemp,
                String dcMaxTemp, String dcSnow, String dcSnowDepth, String dcClouds, String dcWSpeed,
                String dcPrecip, String dcPop)
    {
        this.dcDayNr = dcDayNr;
        this.dcTemp = dcTemp;
        this.dcRh = dcRh;
        this.dcPres = dcPres;
        this.dcMinTemp = dcMinTemp;
        this.dcMaxTemp = dcMaxTemp;
        this.dcSnow = dcSnow;
        this.dcSnowDepth = dcSnowDepth;
        this.dcClouds = dcClouds;
        this.dcWSpeed = dcWSpeed;
        this.dcPrecip = dcPrecip;
        this.dcPop = dcPop;
    }

    public DayCondItem(DayConditions dc, int dayNr)
    {
        setDefault();

        dcDayNr = "Day " + dayNr;
        for(Condition cond : dc.getConditions())
        {
            String message = "";
            switch(cond.getMod())
            {
                case LOWER:
                    message = " < " + cond.getValue();
                    break;
                case LOWER_EQ:
                    message = " <= " + cond.getValue();
                    break;
                case EQUAL:
                    message = " = " + cond.getValue();
                    break;
                case HIGHER:
                    message = " > " + cond.getValue();
                    break;
                case HIGHER_EQ:
                    message = " >= " + cond.getValue();
                    break;
                case DONT_CARE:
                    message = " aici era problema";
                default:
                    message = " doesn't matter.";
            }
            switch(cond.getTip())
            {
                case TEMP:
                    dcTemp = "Temp" + message + "C";
                    break;
                case RH:
                    dcRh = "RH" + message + "%";
                    break;
                case PRES:
                    dcPres = "Pressure" + message + "mb";
                    break;
                case MIN_TEMP:
                    dcMinTemp = "Min temp" + message + "C";
                    break;
                case MAX_TEMP:
                    dcMaxTemp = "Max temp" + message + "C";
                    break;
                case SNOW:
                    dcSnow = "Snow" + message + "mm";
                    break;
                case SNOW_DEPTH:
                    dcSnowDepth = "Snow depth" + message + "mm";
                    break;
                case CLOUDS:
                    dcClouds = "Clouds" + message + "%";
                    break;
                case WND_SPEED:
                    dcWSpeed = "Wind speed" + message + "m/s";
                    break;
                case PRECIP:
                    dcPrecip = "Precipitation" + message + "mm";
                    break;
                case POP:
                    dcPop = "Chance of rain" + message + "%";
                    break;
                default:
                    break;
            }
        }

    }

    private void setDefault()
    {
        dcDayNr = "Day 0";
        dcTemp = "Temp doesn't matter";
        dcRh = "RH doesn't matter";
        dcPres = "Pressure doesn't matter";
        dcMinTemp = "Min temp doesn't matter";
        dcMaxTemp = "Max temp doesn't matter";
        dcSnow = "Snow doesn't matter";
        dcSnowDepth = "Snow depth doesn't matter";
        dcClouds = "Clouds doesn't matter";
        dcWSpeed = "Wind speed doesn't matter";
        dcPrecip = "Precipitation doesn't matter";
        dcPop = "Chance of rain doesn't matter";
    }

    public String getDcDayNr() {
        return dcDayNr;
    }

    public String getDcTemp() {
        return dcTemp;
    }

    public String getDcRh() {
        return dcRh;
    }

    public String getDcPres() {
        return dcPres;
    }

    public String getDcMinTemp() {
        return dcMinTemp;
    }

    public String getDcMaxTemp() {
        return dcMaxTemp;
    }

    public String getDcSnow() {
        return dcSnow;
    }

    public String getDcSnowDepth() {
        return dcSnowDepth;
    }

    public String getDcClouds() {
        return dcClouds;
    }

    public String getDcWSpeed() {
        return dcWSpeed;
    }

    public String getDcPrecip() {
        return dcPrecip;
    }

    public String getDcPop() {
        return dcPop;
    }
}
