package ReceiptService.PositiveTests.Pojo.Requests;

import java.util.HashMap;
import java.util.Map;


public class t1_1_checkSearchData {

    // Здесь создаю тестовые данные, мог сделать в тестовом методе, но вынесу сюда для уменьшения полотна когда в тестовом окне
    public static final String QUERY = "query";
    public static final String TYPE = "type";
    public static final String MAX_CALORIES = "maxCalories";
    public static final String MIN_CALORIES = "minCalories";

    public static Map<String, Object> getParameters1() {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put(QUERY, "fish");
        parameters.put(MAX_CALORIES, 400);
        parameters.put(MIN_CALORIES, 150);
        parameters.put(TYPE, "salad");
        return parameters;
    }


}
