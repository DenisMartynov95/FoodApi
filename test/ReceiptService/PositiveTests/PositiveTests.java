package ReceiptService.PositiveTests;

import ReceiptService.PositiveTests.Pojo.Responses.t1_1checkSearch.Root;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
                    .queryParams(params)
                    .and()
                    .log().all()
                    .get("recipes/complexSearch");
            response.then().assertThat().statusCode(200);
            // Чек статус кода
            assertEquals("Статус-код должен быть 200",200, response.statusCode());

            // Парсинг тела и чек, что тело не пустое
            Root body = response.then().extract().body().as(Root.class);
            assertNotNull("Ответ пуст!",body.toString());

            System.out.println("Количество результатов запроса: " + body.getTotalResults());
            System.out.println(" ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
