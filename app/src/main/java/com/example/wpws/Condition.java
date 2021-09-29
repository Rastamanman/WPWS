package com.example.wpws;

import java.util.Locale;

public class Condition {


    private CondType tip;
    private CondMode mod;
    private float value;

    public Condition() {
        tip = CondType.NONE;
        mod = CondMode.EQUAL;
        value = 0;
    }

    public Condition(CondType type, CondMode mode, float val) {
        tip = type;
        mod = mode;
        value = val;
    }

    public Condition(String whatCond, int whatMode, float val)
    {
        switch(whatCond.toUpperCase()){
            case "TEMPERATURE":
                tip = CondType.TEMP;
                break;
            case "MIN TEMP":
                tip = CondType.MIN_TEMP;
                break;
            case "MAX TEMP":
                tip = CondType.MAX_TEMP;
                break;
            case "RH":
                tip = CondType.RH;
                break;
            case "PRECIPITATION":
                tip = CondType.PRECIP;
                break;
            case "CHANCE OF RAIN":
                tip = CondType.POP;
                break;
            case "SNOW":
                tip = CondType.SNOW;
                break;
            case "CLOUDS":
                tip = CondType.CLOUDS;
                break;
            case "WIND SPEED":
                tip = CondType.WND_SPEED;
                break;
            case "PRESSURE":
                tip = CondType.PRES;
                break;
            default:
                tip = CondType.NONE;
                break;
        }
        switch(whatMode){
            case 0:
                mod = CondMode.DONT_CARE;
                break;
            case 1:
                mod = CondMode.LOWER;
                break;
            case 2:
                mod = CondMode.LOWER_EQ;
                break;
            case 3:
                mod = CondMode.EQUAL;
                break;
            case 4:
                mod = CondMode.HIGHER_EQ;
                break;
            case 5:
                mod = CondMode.HIGHER;
                break;
            default:
                mod = CondMode.DONT_CARE;
                break;
        }
        value = val;
    }

    public boolean checkCond(Day zi) {
        switch (tip) {
            case TEMP:
                return compareTo(zi.getTemp());
            case RH:
                return compareTo(zi.getRh());
            case PRES:
                return compareTo(zi.getPres());
            case MIN_TEMP:
                return compareTo(zi.getMin_temp());
            case MAX_TEMP:
                return compareTo(zi.getMax_temp());
            case SNOW:
                return compareTo(zi.getSnow());
            case SNOW_DEPTH:
                return compareTo(zi.getSnow_depth());
            case CLOUDS:
                return compareTo(zi.getClouds());
            case WND_SPEED:
                return compareTo(zi.getWind_spd());
            case PRECIP:
                return compareTo(zi.getPrecip());
            case POP:
                return compareTo(zi.getPop());
            case SKIP:
                return true;
            default:
                return false;
        }
    }

    public boolean compareTo(int toCompare) {
        switch (mod) {
            case DONT_CARE:
                return true;
            case LOWER:
                return (value < toCompare);
            case LOWER_EQ:
                return (value <= toCompare);
            case EQUAL:
                return (value == toCompare);
            case HIGHER_EQ:
                return (value >= toCompare);
            case HIGHER:
                return (value > toCompare);
            default:
                return false;
        }
    }

    public boolean compareTo(float toCompare) {
        switch (mod) {
            case DONT_CARE:
                return true;
            case LOWER:
                return (value < toCompare);
            case LOWER_EQ:
                return (value <= toCompare);
            case EQUAL:
                return (value == toCompare);
            case HIGHER_EQ:
                return (value >= toCompare);
            case HIGHER:
                return (value > toCompare);
            default:
                return false;
        }
    }

    public CondType getTip() {
        return tip;
    }

    public void setTip(CondType condType) {
        this.tip = condType;
    }

    public CondMode getMod() {
        return mod;
    }

    public void setMod(CondMode mod) {
        this.mod = mod;
    }

    public float getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
