package ReceiptService.PositiveTests.Pojo.Requests;

import java.util.HashMap;
import java.util.Map;

public class t1_4_checkFailedSearch {

    public static final String NUMBER = "1";
    public static final String MIN_CALORIES = "1000";
    public static final String MAX_CALORIES = "1";

    public static Map<String , Object> getParameters(){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("number", NUMBER);
        parameters.put("minCalories", MIN_CALORIES);
        parameters.put("maxCalories", MAX_CALORIES);
        return parameters;
    }


}
