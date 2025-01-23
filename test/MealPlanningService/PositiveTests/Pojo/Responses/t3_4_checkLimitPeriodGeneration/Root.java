package MealPlanningService.PositiveTests.Pojo.Responses.t3_4_checkLimitPeriodGeneration;

public class Root{
    private Week week;


    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }


    public Root(Week week) {
        this.week = week;
    }

    public Root() {
    }

    @Override
    public String toString() {
        return "Root{" +
                "week=" + week +
                '}';
    }
}