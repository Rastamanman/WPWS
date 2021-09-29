package com.example.wpws;

import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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

    public String getDayNr() {
        return dayNr;
    }

    public String getTempText() {
        return tempText;
    }

    public int getTempMode() {
        return tempMode;
    }

    public float getTempVal() {
        return tempVal;
    }

    public String getMinTempText() {
        return minTempText;
    }

    public int getMinTempMode() {
        return minTempMode;
    }

    public float getMinTempVal() {
        return minTempVal;
    }

    public String getMaxTempText() {
        return maxTempText;
    }

    public int getMaxTempMode() {
        return maxTempMode;
    }

    public float getMaxTempVal() {
        return maxTempVal;
    }

    public String getRhText() {
        return rhText;
    }

    public int getRhMode() {
        return rhMode;
    }

    public float getRhVal() {
        return rhVal;
    }

    public String getPrecipText() {
        return precipText;
    }

    public int getPrecipMode() {
        return precipMode;
    }

    public float getPrecipVal() {
        return precipVal;
    }

    public String getCorText() {
        return corText;
    }

    public int getCorMode() {
        return corMode;
    }

    public float getCorVal() {
        return corVal;
    }

    public String getSnowText() {
        return snowText;
    }

    public int getSnowMode() {
        return snowMode;
    }

    public float getSnowVal() {
        return snowVal;
    }

    public String getCloudsText() {
        return cloudsText;
    }

    public int getCloudsMode() {
        return cloudsMode;
    }

    public float getCloudsVal() {
        return cloudsVal;
    }

    public String getWspText() {
        return wspText;
    }

    public int getWspMode() {
        return wspMode;
    }

    public float getWspVal() {
        return wspVal;
    }

    public String getTempSymb() {
        return tempSymb;
    }

    public String getMinTempSymb() {
        return minTempSymb;
    }

    public String getMaxTempSymb() {
        return maxTempSymb;
    }

    public String getRhSymb() {
        return rhSymb;
    }

    public String getPrecipSymb() {
        return precipSymb;
    }

    public String getCorSymb() {
        return corSymb;
    }

    public String getSnowSymb() {
        return snowSymb;
    }

    public String getCloudsSymb() {
        return cloudsSymb;
    }

    public String getWspSymb() {
        return wspSymb;
    }

    public ArrayAdapter<String> getAdapter() {
        return MainActivity.getSpinnerValAdapter();
    }

    public void setDayNr(String dayNr) {
        this.dayNr = dayNr;
    }

    public void setTempText(String tempText) {
        this.tempText = tempText;
    }

    public void setTempMode(int tempMode) {
        this.tempMode = tempMode;
    }

    public void setTempVal(float tempVal) {
        this.tempVal = tempVal;
    }

    public void setTempSymb(String tempSymb) {
        this.tempSymb = tempSymb;
    }

    public void setMinTempText(String minTempText) {
        this.minTempText = minTempText;
    }

    public void setMinTempMode(int minTempMode) {
        this.minTempMode = minTempMode;
    }

    public void setMinTempVal(float minTempVal) {
        this.minTempVal = minTempVal;
    }

    public void setMinTempSymb(String minTempSymb) {
        this.minTempSymb = minTempSymb;
    }

    public void setMaxTempText(String maxTempText) {
        this.maxTempText = maxTempText;
    }

    public void setMaxTempMode(int maxTempMode) {
        this.maxTempMode = maxTempMode;
    }

    public void setMaxTempVal(float maxTempVal) {
        this.maxTempVal = maxTempVal;
    }

    public void setMaxTempSymb(String maxTempSymb) {
        this.maxTempSymb = maxTempSymb;
    }

    public void setRhText(String rhText) {
        this.rhText = rhText;
    }

    public void setRhMode(int rhMode) {
        this.rhMode = rhMode;
    }

    public void setRhVal(float rhVal) {
        this.rhVal = rhVal;
    }

    public void setRhSymb(String rhSymb) {
        this.rhSymb = rhSymb;
    }

    public void setPrecipText(String precipText) {
        this.precipText = precipText;
    }

    public void setPrecipMode(int precipMode) {
        this.precipMode = precipMode;
    }

    public void setPrecipVal(float precipVal) {
        this.precipVal = precipVal;
    }

    public void setPrecipSymb(String precipSymb) {
        this.precipSymb = precipSymb;
    }

    public void setCorText(String corText) {
        this.corText = corText;
    }

    public void setCorMode(int corMode) {
        this.corMode = corMode;
    }

    public void setCorVal(float corVal) {
        this.corVal = corVal;
    }

    public void setCorSymb(String corSymb) {
        this.corSymb = corSymb;
    }

    public void setSnowText(String snowText) {
        this.snowText = snowText;
    }

    public void setSnowMode(int snowMode) {
        this.snowMode = snowMode;
    }

    public void setSnowVal(float snowVal) {
        this.snowVal = snowVal;
    }

    public void setSnowSymb(String snowSymb) {
        this.snowSymb = snowSymb;
    }

    public void setCloudsText(String cloudsText) {
        this.cloudsText = cloudsText;
    }

    public void setCloudsMode(int cloudsMode) {
        this.cloudsMode = cloudsMode;
    }

    public void setCloudsVal(float cloudsVal) {
        this.cloudsVal = cloudsVal;
    }

    public void setCloudsSymb(String cloudsSymb) {
        this.cloudsSymb = cloudsSymb;
    }

    public void setWspText(String wspText) {
        this.wspText = wspText;
    }

    public void setWspMode(int wspMode) {
        this.wspMode = wspMode;
    }

    public void setWspVal(float wspVal) {
        this.wspVal = wspVal;
    }

    public void setWspSymb(String wspSymb) {
        this.wspSymb = wspSymb;
    }

    public String getPresText() {
        return presText;
    }

    public void setPresText(String presText) {
        this.presText = presText;
    }

    public int getPresMode() {
        return presMode;
    }

    public void setPresMode(int presMode) {
        this.presMode = presMode;
    }

    public float getPresVal() {
        return presVal;
    }

    public void setPresVal(float presVal) {
        this.presVal = presVal;
    }

    public String getPresSymb() {
        return presSymb;
    }

    public void setPresSymb(String presSymb) {
        this.presSymb = presSymb;
    }
}
