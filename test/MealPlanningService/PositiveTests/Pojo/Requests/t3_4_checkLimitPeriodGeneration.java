package MealPlanningService.PositiveTests.Pojo.Requests;

import java.util.HashMap;
import java.util.Map;

public class t3_4_checkLimitPeriodGeneration {
    private static final String TIME_FRAME = "year";
    private static final String TARGET_CALORIES = "2000";
    private static final String DIET = "vegetarian";
    private static final String EXCLUDE = null;


    public Map<String,Object> getParameters() {
        Map<String,Object> params = new HashMap<>();
        params.put("timeFrame",TIME_FRAME);
        params.put("targetCalories",TARGET_CALORIES);
        params.put("diet",DIET);
        params.put("exclude",EXCLUDE);
        return params;
    }
}
