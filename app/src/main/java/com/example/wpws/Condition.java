package com.example.wpws;

public class Condition {


    private CondType tip;
    private CondMode mod;
    private int value;

    public Condition() {
        tip = CondType.NONE;
        mod = CondMode.EQUAL;
        value = 0;
    }

    public Condition(CondType type, CondMode mode, int val) {
        tip = type;
        mod = mode;
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
