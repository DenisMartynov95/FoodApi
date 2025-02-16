package MealPlanningService.NegativeTests.Pojo.Responses.t3n_getMealPlanning;

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