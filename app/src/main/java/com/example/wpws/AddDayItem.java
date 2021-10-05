package com.example.wpws;

import android.widget.ArrayAdapter;

public class AddDayItem {

    private String dayNr;
    private String tempText;
    private int tempMode;
    private float tempVal;
    private String tempSymb;
    private String minTempText;
    private int minTempMode;
    private float minTempVal;
    private String minTempSymb;
    private String maxTempText;
    private int maxTempMode;
    private float maxTempVal;
    private String maxTempSymb;
    private String rhText;
    private int rhMode;
    private float rhVal;
    private String rhSymb;
    private String precipText;
    private int precipMode;
    private float precipVal;
    private String precipSymb;
    private String corText;
    private int corMode;
    private float corVal;
    private String corSymb;
    private String snowText;
    private int snowMode;
    private float snowVal;
    private String snowSymb;
    private String presText;
    private int presMode;
    private float presVal;
    private String presSymb;
    private String cloudsText;
    private int cloudsMode;
    private float cloudsVal;
    private String cloudsSymb;
    private String wspText;
    private int wspMode;
    private float wspVal;
    private String wspSymb;

    public AddDayItem(int dayNr) {
        this.dayNr= "Day " + dayNr;
        this.tempText = "Temperature";
        this.tempMode = 0;
        this.tempVal = 0;
        this.tempSymb = "C";
        this.minTempText = "Min Temp";
        this.minTempMode = 0;
        this.minTempVal = 0;
        this.minTempSymb = "C";
        this.maxTempText = "Max Temp";
        this.maxTempMode = 0;
        this.maxTempVal = 0;
        this.maxTempSymb = "C";
        this.rhText = "RH";
        this.rhMode = 0;
        this.rhVal = 0;
        this.rhSymb = "%";
        this.precipText = "Precipitation";
        this.precipMode = 0;
        this.precipVal = 0;
        this.precipSymb = "mm";
        this.corText = "Chance of Rain";
        this.corMode = 0;
        this.corVal = 0;
        this.corSymb = "%";
        this.snowText = "Snow";
        this.snowMode = 0;
        this.snowVal = 0;
        this.snowSymb = "mm";
        this.presText = "Pressure";
        this.presMode = 0;
        this.presVal = 0;
        this.presSymb = "mb";
        this.cloudsText = "Clouds";
        this.cloudsMode = 0;
        this.cloudsVal = 0;
        this.cloudsSymb = "%";
        this.wspText = "Wind Speed";
        this.wspMode = 0;
        this.wspVal = 0;
        this.wspSymb = "m/s";
    }

    public AddDayItem(int dayNr, DayConditions day)
    {
        for(Condition cond : day.getConditions())
        {
            switch (cond.getConditionType()){
                case TEMP:
                    this.tempVal = cond.getValue();
                    switch (cond.getConditionMode()){
                        case DONT_CARE:
                            this.tempMode = 0;
                            break;
                        case LOWER:
                            this.tempMode = 1;
                            break;
                        case LOWER_EQ:
                            this.tempMode = 2;
                            break;
                        case EQUAL:
                            this.tempMode = 3;
                            break;
                        case HIGHER_EQ:
                            this.tempMode = 4;
                            break;
                        case HIGHER:
                            this.tempMode = 5;
                            break;
                        default:
                            this.tempMode = 0;
                            break;
                    }
                    break;
                case MIN_TEMP:
                    this.minTempVal = cond.getValue();
                    switch (cond.getConditionMode()){
                        case DONT_CARE:
                            this.minTempMode = 0;
                            break;
                        case LOWER:
                            this.minTempMode = 1;
                            break;
                        case LOWER_EQ:
                            this.minTempMode = 2;
                            break;
                        case EQUAL:
                            this.minTempMode = 3;
                            break;
                        case HIGHER_EQ:
                            this.minTempMode = 4;
                            break;
                        case HIGHER:
                            this.minTempMode = 5;
                            break;
                        default:
                            this.minTempMode = 0;
                            break;
                    }
                    break;
                case MAX_TEMP:
                    this.maxTempVal = cond.getValue();
                    switch (cond.getConditionMode()){
                        case DONT_CARE:
                            this.maxTempMode = 0;
                            break;
                        case LOWER:
                            this.maxTempMode = 1;
                            break;
                        case LOWER_EQ:
                            this.maxTempMode = 2;
                            break;
                        case EQUAL:
                            this.maxTempMode = 3;
                            break;
                        case HIGHER_EQ:
                            this.maxTempMode = 4;
                            break;
                        case HIGHER:
                            this.maxTempMode = 5;
                            break;
                        default:
                            this.maxTempMode = 0;
                            break;
                    }
                    break;
                case RH:
                    this.rhVal = cond.getValue();
                    switch (cond.getConditionMode()){
                        case DONT_CARE:
                            this.rhMode = 0;
                            break;
                        case LOWER:
                            this.rhMode = 1;
                            break;
                        case LOWER_EQ:
                            this.rhMode = 2;
                            break;
                        case EQUAL:
                            this.rhMode = 3;
                            break;
                        case HIGHER_EQ:
                            this.rhMode = 4;
                            break;
                        case HIGHER:
                            this.rhMode = 5;
                            break;
                        default:
                            this.rhMode = 0;
                            break;
                    }
                    break;
                case PRECIP:
                    this.precipVal = cond.getValue();
                    switch (cond.getConditionMode()){
                        case DONT_CARE:
                            this.precipMode = 0;
                            break;
                        case LOWER:
                            this.precipMode = 1;
                            break;
                        case LOWER_EQ:
                            this.precipMode = 2;
                            break;
                        case EQUAL:
                            this.precipMode = 3;
                            break;
                        case HIGHER_EQ:
                            this.precipMode = 4;
                            break;
                        case HIGHER:
                            this.precipMode = 5;
                            break;
                        default:
                            this.precipMode = 0;
                            break;
                    }
                    break;
                case POP:
                    this.corVal = cond.getValue();
                    switch (cond.getConditionMode()){
                        case DONT_CARE:
                            this.corMode = 0;
                            break;
                        case LOWER:
                            this.corMode = 1;
                            break;
                        case LOWER_EQ:
                            this.corMode = 2;
                            break;
                        case EQUAL:
                            this.corMode = 3;
                            break;
                        case HIGHER_EQ:
                            this.corMode = 4;
                            break;
                        case HIGHER:
                            this.corMode = 5;
                            break;
                        default:
                            this.corMode = 0;
                            break;
                    }
                    break;
                case SNOW:
                    this.snowVal = cond.getValue();
                    switch (cond.getConditionMode()){
                        case DONT_CARE:
                            this.snowMode = 0;
                            break;
                        case LOWER:
                            this.snowMode = 1;
                            break;
                        case LOWER_EQ:
                            this.snowMode = 2;
                            break;
                        case EQUAL:
                            this.snowMode = 3;
                            break;
                        case HIGHER_EQ:
                            this.snowMode = 4;
                            break;
                        case HIGHER:
                            this.snowMode = 5;
                            break;
                        default:
                            this.snowMode = 0;
                            break;
                    }
                    break;
                case PRES:
                    this.presVal = cond.getValue();
                    switch (cond.getConditionMode()){
                        case DONT_CARE:
                            this.presMode = 0;
                            break;
                        case LOWER:
                            this.presMode = 1;
                            break;
                        case LOWER_EQ:
                            this.presMode = 2;
                            break;
                        case EQUAL:
                            this.presMode = 3;
                            break;
                        case HIGHER_EQ:
                            this.presMode = 4;
                            break;
                        case HIGHER:
                            this.presMode = 5;
                            break;
                        default:
                            this.presMode = 0;
                            break;
                    }
                    break;
                case CLOUDS:
                    this.cloudsVal = cond.getValue();
                    switch (cond.getConditionMode()){
                        case DONT_CARE:
                            this.cloudsMode = 0;
                            break;
                        case LOWER:
                            this.cloudsMode = 1;
                            break;
                        case LOWER_EQ:
                            this.cloudsMode = 2;
                            break;
                        case EQUAL:
                            this.cloudsMode = 3;
                            break;
                        case HIGHER_EQ:
                            this.cloudsMode = 4;
                            break;
                        case HIGHER:
                            this.cloudsMode = 5;
                            break;
                        default:
                            this.cloudsMode = 0;
                            break;
                    }
                    break;
                case WND_SPEED:
                    this.wspVal = cond.getValue();
                    switch (cond.getConditionMode()){
                        case DONT_CARE:
                            this.wspMode = 0;
                            break;
                        case LOWER:
                            this.wspMode = 1;
                            break;
                        case LOWER_EQ:
                            this.wspMode = 2;
                            break;
                        case EQUAL:
                            this.wspMode = 3;
                            break;
                        case HIGHER_EQ:
                            this.wspMode = 4;
                            break;
                        case HIGHER:
                            this.wspMode = 5;
                            break;
                        default:
                            this.wspMode = 0;
                            break;
                    }
                    break;
                default:
                    break;
            }
        }

        this.dayNr= "Day " + dayNr;
        this.tempText = "Temperature";
        this.tempSymb = "C";
        this.minTempText = "Min Temp";
        this.minTempSymb = "C";
        this.maxTempText = "Max Temp";
        this.maxTempSymb = "C";
        this.rhText = "RH";
        this.rhSymb = "%";
        this.precipText = "Precipitation";
        this.precipSymb = "mm";
        this.corText = "Chance of Rain";
        this.corSymb = "%";
        this.snowText = "Snow";
        this.snowSymb = "mm";
        this.presText = "Pressure";
        this.presSymb = "mb";
        this.cloudsText = "Clouds";
        this.cloudsSymb = "%";
        this.wspText = "Wind Speed";
        this.wspSymb = "m/s";
    }

    public String getDayNumber() {
        return dayNr;
    }

    public String getTempText() {
        return tempText;
    }

    public int getTempMode() {
        return tempMode;
    }

    public float getTempValue() {
        return tempVal;
    }

    public String getMinTempText() {
        return minTempText;
    }

    public int getMinTempMode() {
        return minTempMode;
    }

    public float getMinTempValue() {
        return minTempVal;
    }

    public String getMaxTempText() {
        return maxTempText;
    }

    public int getMaxTempMode() {
        return maxTempMode;
    }

    public float getMaxTempValue() {
        return maxTempVal;
    }

    public String getRhText() {
        return rhText;
    }

    public int getRhMode() {
        return rhMode;
    }

    public float getRhValue() {
        return rhVal;
    }

    public String getPrecipitationText() {
        return precipText;
    }

    public int getPrecipitationMode() {
        return precipMode;
    }

    public float getPrecipitationValue() {
        return precipVal;
    }

    public String getRainText() {
        return corText;
    }

    public int getRainMode() {
        return corMode;
    }

    public float getRainValue() {
        return corVal;
    }

    public String getSnowText() {
        return snowText;
    }

    public int getSnowMode() {
        return snowMode;
    }

    public float getSnowValue() {
        return snowVal;
    }

    public String getCloudsText() {
        return cloudsText;
    }

    public int getCloudsMode() {
        return cloudsMode;
    }

    public float getCloudsValue() {
        return cloudsVal;
    }

    public String getWindSpeedText() {
        return wspText;
    }

    public int getWindSpeedMode() {
        return wspMode;
    }

    public float getWindSpeedValue() {
        return wspVal;
    }

    public String getTempSymbol() {
        return tempSymb;
    }

    public String getMinTempSymbol() {
        return minTempSymb;
    }

    public String getMaxTempSymbol() {
        return maxTempSymb;
    }

    public String getRhSymbol() {
        return rhSymb;
    }

    public String getPrecipitationSymbol() {
        return precipSymb;
    }

    public String getRainSymbol() {
        return corSymb;
    }

    public String getSnowSymbol() {
        return snowSymb;
    }

    public String getCloudsSymbol() {
        return cloudsSymb;
    }

    public String getWindSpeedSymbol() {
        return wspSymb;
    }

    public ArrayAdapter<String> getAdapter() {
        return MainActivity.getSpinnerValAdapter();
    }

    public void setDayNumber(int dayNumber) {
        this.dayNr = "Day " + dayNumber;
    }

    public void setTempText(String tempText) {
        this.tempText = tempText;
    }

    public void setTempMode(int tempMode) {
        this.tempMode = tempMode;
    }

    public void setTempValue(float tempVal) {
        this.tempVal = tempVal;
    }

    public void setTempSymbol(String tempSymb) {
        this.tempSymb = tempSymb;
    }

    public void setMinTempText(String minTempText) {
        this.minTempText = minTempText;
    }

    public void setMinTempMode(int minTempMode) {
        this.minTempMode = minTempMode;
    }

    public void setMinTempValue(float minTempVal) {
        this.minTempVal = minTempVal;
    }

    public void setMinTempSymbol(String minTempSymb) {
        this.minTempSymb = minTempSymb;
    }

    public void setMaxTempText(String maxTempText) {
        this.maxTempText = maxTempText;
    }

    public void setMaxTempMode(int maxTempMode) {
        this.maxTempMode = maxTempMode;
    }

    public void setMaxTempValue(float maxTempVal) {
        this.maxTempVal = maxTempVal;
    }

    public void setMaxTempSymbol(String maxTempSymb) {
        this.maxTempSymb = maxTempSymb;
    }

    public void setRhText(String rhText) {
        this.rhText = rhText;
    }

    public void setRhMode(int rhMode) {
        this.rhMode = rhMode;
    }

    public void setRhValue(float rhVal) {
        this.rhVal = rhVal;
    }

    public void setRhSymbol(String rhSymb) {
        this.rhSymb = rhSymb;
    }

    public void setPrecipitationText(String precipText) {
        this.precipText = precipText;
    }

    public void setPrecipitationMode(int precipMode) {
        this.precipMode = precipMode;
    }

    public void setPrecipitationValue(float precipVal) {
        this.precipVal = precipVal;
    }

    public void setPrecipitationSymbol(String precipSymb) {
        this.precipSymb = precipSymb;
    }

    public void setRainText(String corText) {
        this.corText = corText;
    }

    public void setRainMode(int corMode) {
        this.corMode = corMode;
    }

    public void setRainValue(float corVal) {
        this.corVal = corVal;
    }

    public void setRainSymbol(String corSymb) {
        this.corSymb = corSymb;
    }

    public void setSnowText(String snowText) {
        this.snowText = snowText;
    }

    public void setSnowMode(int snowMode) {
        this.snowMode = snowMode;
    }

    public void setSnowValue(float snowVal) {
        this.snowVal = snowVal;
    }

    public void setSnowSymbol(String snowSymb) {
        this.snowSymb = snowSymb;
    }

    public void setCloudsText(String cloudsText) {
        this.cloudsText = cloudsText;
    }

    public void setCloudsMode(int cloudsMode) {
        this.cloudsMode = cloudsMode;
    }

    public void setCloudsValue(float cloudsVal) {
        this.cloudsVal = cloudsVal;
    }

    public void setCloudsSymbol(String cloudsSymb) {
        this.cloudsSymb = cloudsSymb;
    }

    public void setWindSpeedText(String wspText) {
        this.wspText = wspText;
    }

    public void setWindSpeedMode(int wspMode) {
        this.wspMode = wspMode;
    }

    public void setWindSpeedValue(float wspVal) {
        this.wspVal = wspVal;
    }

    public void setWindSpeedSymbol(String wspSymb) {
        this.wspSymb = wspSymb;
    }

    public String getPressureText() {
        return presText;
    }

    public void setPresText(String presText) {
        this.presText = presText;
    }

    public int getPressureMode() {
        return presMode;
    }

    public void setPressureMode(int presMode) {
        this.presMode = presMode;
    }

    public float getPressureValue() {
        return presVal;
    }

    public void setPressureValue(float presVal) {
        this.presVal = presVal;
    }

    public String getPressureSymbol() {
        return presSymb;
    }

    public void setPressureSymbol(String presSymb) {
        this.presSymb = presSymb;
    }
}
