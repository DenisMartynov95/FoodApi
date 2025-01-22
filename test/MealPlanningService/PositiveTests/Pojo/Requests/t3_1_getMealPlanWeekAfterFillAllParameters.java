package MealPlanningService.PositiveTests.Pojo.Requests;

import java.util.HashMap;
import java.util.Map;

public class t3_1_getMealPlanWeekAfterFillAllParameters {

    // Здесь создаю тестовые данные, мог сделать в тестовом методе, но вынесу сюда для уменьшения полотна когда в тестовом окне
    public static final String TIME_FRAME = "timeFrame";
    public static final String TARGET_CALORIES = "targetCalories";
    public static final String DIET = "diet";
    public static final String EXCLUDE = "exclude";

    public static Map<String,Object> getParameters() {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put(TIME_FRAME, "week");
        parameters.put(TARGET_CALORIES, "2000");
        parameters.put(DIET, "diet");
        parameters.put(EXCLUDE, "shellfish");
        return parameters;
    }

}
