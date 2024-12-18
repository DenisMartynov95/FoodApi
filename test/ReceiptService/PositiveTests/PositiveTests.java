package ReceiptService.PositiveTests;

import ReceiptService.PositiveTests.Pojo.Responses.t1_1checkSearch.Result;
import ReceiptService.PositiveTests.Pojo.Responses.t1_1checkSearch.Root;
import ReceiptService.ReceiptBaseSettings;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ReceiptService.PositiveTests.Pojo.Requests.*;
import org.junit.runners.Parameterized;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

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
    @DisplayName("Работоспособность поиска на сервисе")
    public void t1_1_checkSearch() {
        try {
            Map<String, Object> params = t1_1_checkSearchData.getParameters();
            ReceiptBaseSettings receiptBaseSettings = new ReceiptBaseSettings();

            Response response = given()
                    .spec(receiptBaseSettings.getSpec())
                    .queryParams(params)
                    .and()
//                    .log().all()
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

    @Step
    @Test
    @DisplayName("Работоспособность ограничителя количества выданных запросов")
    @Description("Ограничитель должен стоять в 100 запросов, не больше!")
    public void t1_2_checkMaxLimitsSearch(){
        try {
            Map<String, Object> params = t1_2_checkLimitsSearch.getParameters();
            ReceiptBaseSettings receiptBaseSettings = new ReceiptBaseSettings();

            Response response = given()
                    .spec(receiptBaseSettings.getSpec())
                    .queryParams(params)
                    .log().all()
                    .and()
                    .get("recipes/complexSearch");
            response.then().assertThat().statusCode(200);
            // Чек статус-кода
            assertEquals("Статус-код должен быть 200", 200, response.statusCode());
            // Чек, что тело не пустое
            Root body = response.then().extract().body().as(Root.class);
            assertNotNull("Ответ пуст!", body.toString());
            // Чек, что ограничитель сработал
            if (body.getNumber() == 100) {
                System.out.println("Ограничение сработало! В запросе 100 ответов");
                System.out.println(body.getNumber());
            } else {
                System.out.println("Ограничение не сработало!");
                System.out.println(body.getNumber());
            }
            // Подсчет количества id пришедшего запроса
            int count = 0;
            int expected = 100;
            for (Result result : body.getResults()) {
                if(result.getId() != 0) {
                    count ++;
                } else {
                    System.out.println("Ошибка подсчета: " + result.getId());
                }
                assertEquals("Количество айдишников не совпадает!",expected,count);
            }
            System.out.println("Количество айдишников: " + count);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Step
    @Test
    @DisplayName("Работоспособность ограничителя количества выданных запросов")
    @Description("Должен прийти один запрос, даже если параметр number = 0")
    public void t1_3_checkMinLimitsSearch(){
        ReceiptBaseSettings receiptBaseSettings = new ReceiptBaseSettings();

        Response response = given()
                .spec(receiptBaseSettings.getSpec())
                .queryParams("number", 0)
//                .log().all()
                .get("recipes/complexSearch");
        response.then().statusCode(200);
        // Чек, что тело не пустое
        // Ошибка тут, надо сделать pojo и туда разложить ответ и метод стринга туда запихнуть!
        Root root = response.then().extract().as(Root.class);
        assertNotNull(root.getResults());
        System.out.println("Тело запроса не пустое!" + root.getResults().toString());
    }


}
