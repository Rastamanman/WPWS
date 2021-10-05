package com.example.wpws;

import java.util.ArrayList;
import java.util.List;

public class Alarm {

    private Location location;
    private String name;
    private List<DayConditions> dayConditions;

    public Alarm()
    {
        location = new Location();
        name = "";
        dayConditions = new ArrayList<>();
    }

    public Alarm(Location location, String name)
    {
        this.location = location;
        this.name = name;
        dayConditions = new ArrayList<>();
    }

    public void setLocation(Location locationToSet) { location = locationToSet; }

    public Location getLocation() { return location; }

    public void setName(String nameToSet) { name = nameToSet; }

    public String getName() { return name; }

    public List<DayConditions> getDays() { return dayConditions; }

    public void setConditions(List<DayConditions> conditionsToSet) { dayConditions = conditionsToSet; }

    public void addDay(DayConditions day) { dayConditions.add(day); }

    public void addCondition(int dayIndex, Condition condition) { dayConditions.get(dayIndex).addCondition(condition); }

}
