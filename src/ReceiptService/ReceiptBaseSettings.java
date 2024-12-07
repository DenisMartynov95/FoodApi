package ReceiptService;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;



public class ReceiptBaseSettings {

    public RequestSpecification spec;
    private final String API_KEY = "e044a24e1ccc449a819845cc01fd9870";

    public String getApiKey() {
        return API_KEY;
    }
    public ReceiptBaseSettings() {
        spec = RestAssured.given()
                .baseUri("https://api.spoonacular.com/")
                .contentType("Content-Type: application/json")
                .header("x-api-key",getApiKey());
    }


}
