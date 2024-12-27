package ReceiptService;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class MealPlanningBaseSettings {

    public RequestSpecification spec;
    private final String API_KEY = "e044a24e1ccc449a819845cc01fd9870";

    public String getAPI_KEY() {
        return API_KEY;
    }
    // Только для теста №2 - только для регистрации, далее нужен второй спек
    public MealPlanningBaseSettings() {
        spec = RestAssured.given()
                .baseUri("https://api.spoonacular.com/")
                .contentType("application/json")
                .header("x-api-key",getAPI_KEY());
    }


    public RequestSpecification getSpec() {
        return spec;
    }
}
