package ReceiptService.PositiveTests.Pojo.Requests.t1;

import java.util.HashMap;
import java.util.Map;

public class t1_2_checkLimitsSearch {

    public static final String NUMBER = "500";

    public static Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("number", NUMBER);
        return parameters;
    }
}
