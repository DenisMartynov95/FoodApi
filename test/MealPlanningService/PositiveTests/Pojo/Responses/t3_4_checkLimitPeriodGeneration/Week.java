package MealPlanningService.PositiveTests.Pojo.Responses.t3_4_checkLimitPeriodGeneration;

import java.util.HashMap;
import java.util.Map;

public class Week {
    private Monday monday;
    private Tuesday tuesday;
    private Wednesday wednesday;
    private Thursday thursday;
    private Friday friday;
    private Saturday saturday;
    private Sunday sunday;


    public Monday getMonday() {
        return monday;
    }

    public void setMonday(Monday monday) {
        this.monday = monday;
    }

    public Tuesday getTuesday() {
        return tuesday;
    }

    public void setTuesday(Tuesday tuesday) {
        this.tuesday = tuesday;
    }

    public Wednesday getWednesday() {
        return wednesday;
    }

    public void setWednesday(Wednesday wednesday) {
        this.wednesday = wednesday;
    }

    public Thursday getThursday() {
        return thursday;
    }

    public void setThursday(Thursday thursday) {
        this.thursday = thursday;
    }

    public Friday getFriday() {
        return friday;
    }

    public void setFriday(Friday friday) {
        this.friday = friday;
    }

    public Saturday getSaturday() {
        return saturday;
    }

    public void setSaturday(Saturday saturday) {
        this.saturday = saturday;
    }

    public Sunday getSunday() {
        return sunday;
    }

    public void setSunday(Sunday sunday) {
        this.sunday = sunday;
    }

    public Week() {
    }

    @Override
    public String toString() {
        return "Week{" +
                "monday=" + monday +
                ", tuesday=" + tuesday +
                ", wednesday=" + wednesday +
                ", thursday=" + thursday +
                ", friday=" + friday +
                ", saturday=" + saturday +
                ", sunday=" + sunday +
                '}';
    }

    // Нужен метод, который поможет мне получать все данные от всех дней недели

    public Map<String, Object> getAllDaysData() {
        Map<String, Object> allDaysData = new HashMap<>();

        allDaysData.put("Monday", this.monday.getMeals());
        allDaysData.put("Tuesday", this.tuesday.getMeals());
        allDaysData.put("Wednesday", this.wednesday.getMeals());
        allDaysData.put("Thursday", this.thursday.getMeals());
        allDaysData.put("Friday", this.friday.getMeals());
        allDaysData.put("Saturday", this.saturday.getMeals());
        allDaysData.put("Sunday", this.sunday.getMeals());

        return allDaysData;
    }

}

