package com.example.wpws;

import java.util.ArrayList;
import java.util.List;

public class DayConditions {

    private List<Condition> conditions;

    public DayConditions()
    {
        conditions = new ArrayList<>();
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

}
