package MealPlanningService.PositiveTests.Pojo.Requests;

import java.util.HashMap;
import java.util.Map;

public class t3_6_checkCalLimit {
    private static final String TIME_FRAME = "day";
    private static final String TARGET_CALORIES = "2000";

    public Map<String,Object> getParameters() {
        Map<String, Object> params = new HashMap<>();
        params.put("timeFrame", TIME_FRAME);
        params.put("targetCalories", TARGET_CALORIES);
        return  params;
    }

}
