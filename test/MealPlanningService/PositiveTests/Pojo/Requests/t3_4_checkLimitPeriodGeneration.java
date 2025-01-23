package MealPlanningService.PositiveTests.Pojo.Requests;

public class t3_4_checkLimitPeriodGeneration {
    private String timeFrame;
    private String targetCalories;
    private String diet;
    private String exclude;

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    public String getTargetCalories() {
        return targetCalories;
    }

    public void setTargetCalories(String targetCalories) {
        this.targetCalories = targetCalories;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getExclude() {
        return exclude;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public t3_4_checkLimitPeriodGeneration() {
    }

    public t3_4_checkLimitPeriodGeneration(String timeFrame, String targetCalories, String diet, String exclude) {
        this.timeFrame = timeFrame;
        this.targetCalories = targetCalories;
        this.diet = diet;
        this.exclude = exclude;
    }
}
