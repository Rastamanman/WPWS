package com.example.wpws;

import java.util.ArrayList;
import java.util.List;

public class DayConditions {

    private List<Condition> conditions;

    public DayConditions()
    {
        conditions = new ArrayList<>();
    }

    public DayConditions(AddDayItem toAdd)
    {
        conditions = new ArrayList<>();
        this.buildConditions(toAdd);
    }

    public DayConditions(List<Condition> condsToAdd)
    {
        conditions = condsToAdd;
    }

    public List<Condition> getConditions() { return conditions; }

    public boolean checkThisDay(Day dayToCheck)
    {
        boolean result = true;
        for(Condition cond : conditions)
        {
            result &= cond.checkCond(dayToCheck);
        }
        return result;
    }

    public void addCondition(Condition condToAdd)
    {
        conditions.add(condToAdd);
    }

    public void buildConditions(AddDayItem toBuild)
    {
        // 0 = don't care(d.c.)
        //temp
        if(toBuild.getTempMode() != 0)
            addCondition(new Condition(toBuild.getTempText(), toBuild.getTempMode(), toBuild.getTempVal()));
        //min temp
        if(toBuild.getMinTempMode() != 0)
            addCondition(new Condition(toBuild.getMinTempText(), toBuild.getMinTempMode(), toBuild.getMinTempVal()));
        //max temp
        if(toBuild.getMaxTempMode() != 0)
            addCondition(new Condition(toBuild.getMaxTempText(), toBuild.getMaxTempMode(), toBuild.getMaxTempVal()));
        //rh
        if(toBuild.getRhMode() != 0)
            addCondition(new Condition(toBuild.getRhText(), toBuild.getRhMode(), toBuild.getRhVal()));
        //precip
        if(toBuild.getPrecipMode() != 0)
            addCondition(new Condition(toBuild.getPrecipText(), toBuild.getPrecipMode(), toBuild.getPrecipVal()));
        //cor(pop)
        if(toBuild.getCorMode() != 0)
            addCondition(new Condition(toBuild.getCorText(), toBuild.getCorMode(), toBuild.getCorVal()));
        //snow
        if(toBuild.getSnowMode() != 0)
            addCondition(new Condition(toBuild.getSnowText(), toBuild.getSnowMode(), toBuild.getSnowVal()));
        //pressure
        if(toBuild.getPresMode() != 0)
            addCondition(new Condition(toBuild.getPresText(), toBuild.getPresMode(), toBuild.getPresVal()));
        //clouds
        if(toBuild.getCloudsMode() != 0)
            addCondition(new Condition(toBuild.getCloudsText(), toBuild.getCloudsMode(), toBuild.getCloudsVal()));
        //wind speed
        if(toBuild.getWspMode() != 0)
            addCondition(new Condition(toBuild.getWspText(), toBuild.getWspMode(), toBuild.getWspVal()));
    }

}
