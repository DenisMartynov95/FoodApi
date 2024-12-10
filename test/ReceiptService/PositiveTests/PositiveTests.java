package ReceiptService.PositiveTests;

import ReceiptService.ReceiptBaseSettings;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ReceiptService.PositiveTests.Pojo.Requests.*;
import org.junit.runners.Parameterized;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class PositiveTests {

//    @Before
//    public void setUp() {
//        ReceiptBaseSettings receiptSpec = new ReceiptBaseSettings();
//    }

    // Тест-сьют по ручке поиска рецептов
    // №1 - базовая проверка, что поиск работает и возвращает данные
    // №2 - № 3 - проверка ограничителя выдачи результатов (от 1 до 100)
    @Step
    @Test
    @DisplayName("Работоспособность поиск на сервисе")
    public void t1_1_checkSearch() {
        try {
            Map<String, Object> params = t1_1_checkSearchData.getParameters1();
            ReceiptBaseSettings receiptBaseSettings = new ReceiptBaseSettings();

            Response response = given()
                    .spec(receiptBaseSettings.getSpec())
                    .params(params)
                    .and()
                    .get("recipes/complexSearch");
            response.then().assertThat().statusCode(200);
            if (response.statusCode() == 200) {
                System.out.println("Статус код 200");
            } else {
                System.out.println("Статус код не 200! Тест провален" + response.statusCode());
            }




        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
