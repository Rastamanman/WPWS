package com.example.wpws;

public class Condition {


    private ConditionType conditionType;
    private ConditionMode conditionMode;
    private float value;

    public Condition() {
        conditionType = ConditionType.NONE;
        conditionMode = ConditionMode.EQUAL;
        value = 0;
    }

    public Condition(ConditionType type, ConditionMode mode, float value) {
        conditionType = type;
        conditionMode = mode;
        value = value;
    }

    public Condition(String whatCondition, int whatMode, float value)
    {
        switch(whatCondition.toUpperCase()){
            case "TEMPERATURE":
                conditionType = ConditionType.TEMP;
                break;
            case "MIN TEMP":
                conditionType = ConditionType.MIN_TEMP;
                break;
            case "MAX TEMP":
                conditionType = ConditionType.MAX_TEMP;
                break;
            case "RH":
                conditionType = ConditionType.RH;
                break;
            case "PRECIPITATION":
                conditionType = ConditionType.PRECIP;
                break;
            case "CHANCE OF RAIN":
                conditionType = ConditionType.POP;
                break;
            case "SNOW":
                conditionType = ConditionType.SNOW;
                break;
            case "CLOUDS":
                conditionType = ConditionType.CLOUDS;
                break;
            case "WIND SPEED":
                conditionType = ConditionType.WND_SPEED;
                break;
            case "PRESSURE":
                conditionType = ConditionType.PRES;
                break;
            default:
                conditionType = ConditionType.NONE;
                break;
        }
        switch(whatMode){
            case 0:
                conditionMode = ConditionMode.DONT_CARE;
                break;
            case 1:
                conditionMode = ConditionMode.LOWER;
                break;
            case 2:
                conditionMode = ConditionMode.LOWER_EQ;
                break;
            case 3:
                conditionMode = ConditionMode.EQUAL;
                break;
            case 4:
                conditionMode = ConditionMode.HIGHER_EQ;
                break;
            case 5:
                conditionMode = ConditionMode.HIGHER;
                break;
            default:
                conditionMode = ConditionMode.DONT_CARE;
                break;
        }
        this.value = value;
    }

    public boolean checkConditionFor(Day zi) {
        switch (conditionType) {
            case TEMP:
                return compareTo(zi.getTemperature());
            case RH:
                return compareTo(zi.getRh());
            case PRES:
                return compareTo(zi.getPressure());
            case MIN_TEMP:
                return compareTo(zi.getMinTemp());
            case MAX_TEMP:
                return compareTo(zi.getMaxTemp());
            case SNOW:
                return compareTo(zi.getSnow());
            case SNOW_DEPTH:
                return compareTo(zi.getSnowDepth());
            case CLOUDS:
                return compareTo(zi.getClouds());
            case WND_SPEED:
                return compareTo(zi.getWindSpeed());
            case PRECIP:
                return compareTo(zi.getPrecipitation());
            case POP:
                return compareTo(zi.getRain());
            case SKIP:
                return true;
            default:
                return false;
        }
    }

    public boolean compareTo(int toCompare) {
        switch (conditionMode) {
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
        switch (conditionMode) {
            case DONT_CARE:
                return true;
            case LOWER:
                return (value > toCompare);
            case LOWER_EQ:
                return (value >= toCompare);
            case EQUAL:
                return (value == toCompare);
            case HIGHER_EQ:
                return (value <= toCompare);
            case HIGHER:
                return (value < toCompare);
            default:
                return false;
        }
    }

    public ConditionType getConditionType() {
        return conditionType;
    }

    public void setConditionType(ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    public ConditionMode getConditionMode() {
        return conditionMode;
    }

    public void setConditionMode(ConditionMode conditionMode) {
        this.conditionMode = conditionMode;
    }

    public float getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
