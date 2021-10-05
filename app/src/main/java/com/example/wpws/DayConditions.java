package com.example.wpws;

import java.util.ArrayList;
import java.util.List;

public class DayConditions {

    private List<Condition> conditions;

    public DayConditions()
    {
        conditions = new ArrayList<>();
    }

    public DayConditions(AddDayItem dayItem)
    {
        conditions = new ArrayList<>();
        this.buildConditionsFrom(dayItem);
    }

    public DayConditions(List<Condition> conditions)
    {
        this.conditions = conditions;
    }

    public List<Condition> getConditions() { return conditions; }

    public boolean checkThisDay(Day dayToCheck)
    {
        boolean result = true;
        for(Condition condition : conditions)
        {
            result &= condition.checkConditionFor(dayToCheck);
        }
        return result;
    }

    public void addCondition(Condition condition)
    {
        conditions.add(condition);
    }

    public void buildConditionsFrom(AddDayItem dayItem)
    {
        // 0 = don't care(d.c.)
        //temp
        if(dayItem.getTempMode() != 0)
            addCondition(new Condition(dayItem.getTempText(), dayItem.getTempMode(), dayItem.getTempValue()));
        //min temp
        if(dayItem.getMinTempMode() != 0)
            addCondition(new Condition(dayItem.getMinTempText(), dayItem.getMinTempMode(), dayItem.getMinTempValue()));
        //max temp
        if(dayItem.getMaxTempMode() != 0)
            addCondition(new Condition(dayItem.getMaxTempText(), dayItem.getMaxTempMode(), dayItem.getMaxTempValue()));
        //rh
        if(dayItem.getRhMode() != 0)
            addCondition(new Condition(dayItem.getRhText(), dayItem.getRhMode(), dayItem.getRhValue()));
        //precip
        if(dayItem.getPrecipitationMode() != 0)
            addCondition(new Condition(dayItem.getPrecipitationText(), dayItem.getPrecipitationMode(), dayItem.getPrecipitationValue()));
        //cor(pop)
        if(dayItem.getRainMode() != 0)
            addCondition(new Condition(dayItem.getRainText(), dayItem.getRainMode(), dayItem.getRainValue()));
        //snow
        if(dayItem.getSnowMode() != 0)
            addCondition(new Condition(dayItem.getSnowText(), dayItem.getSnowMode(), dayItem.getSnowValue()));
        //pressure
        if(dayItem.getPressureMode() != 0)
            addCondition(new Condition(dayItem.getPressureText(), dayItem.getPressureMode(), dayItem.getPressureValue()));
        //clouds
        if(dayItem.getCloudsMode() != 0)
            addCondition(new Condition(dayItem.getCloudsText(), dayItem.getCloudsMode(), dayItem.getCloudsValue()));
        //wind speed
        if(dayItem.getWindSpeedMode() != 0)
            addCondition(new Condition(dayItem.getWindSpeedText(), dayItem.getWindSpeedMode(), dayItem.getWindSpeedValue()));
    }

}
