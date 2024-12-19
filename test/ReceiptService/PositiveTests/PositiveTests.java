package ReceiptService.PositiveTests;

import ReceiptService.PositiveTests.Pojo.Responses.t1_1checkSearch.Result;
import ReceiptService.PositiveTests.Pojo.Responses.t1_1checkSearch.Root;
import ReceiptService.ReceiptBaseSettings;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
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

    // Тест-сьют по ручке поиска рецептов recipes/complexSearch
    // №1 - базовая проверка, что поиск работает и возвращает данные
    // №2 - № 3 - проверка ограничителя выдачи результатов (от 1 до 100)
    // №4 - проверка, что при некорректно запросе - приходят валидные данные

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
            assertEquals("Статус-код должен быть 200", 200, response.statusCode());

            // Парсинг тела и чек, что тело не пустое
            Root body = response.then().extract().body().as(Root.class);
            assertNotNull("Ответ пуст!", body.toString());

            System.out.println("Количество результатов запроса: " + body.getTotalResults());
            System.out.println("Тест №1_1 прошел успешно!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Step
    @Test
    @DisplayName("Работоспособность ограничителя количества выданных запросов")
    @Description("Ограничитель должен стоять в 100 запросов, не больше!")
    public void t1_2_checkMaxLimitsSearch() {
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
                if (result.getId() != 0) {
                    count++;
                } else {
                    System.out.println("Ошибка подсчета: " + result.getId());
                }
                assertEquals("Количество айдишников не совпадает!", expected, count);
            }
            System.out.println("Количество айдишников: " + count);
            System.out.println("Тест №1_2 прошел успешно!");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Step
    @Test
    @DisplayName("Работоспособность ограничителя количества выданных запросов")
    @Description("Должен прийти один запрос, даже если параметр number = 0")
    public void t1_3_checkMinLimitsSearch() {

        try {
            ReceiptBaseSettings receiptBaseSettings = new ReceiptBaseSettings();

            Response response = given()
                    .spec(receiptBaseSettings.getSpec())
                    .queryParams("number", 0)
//                .log().all()
                    .get("recipes/complexSearch");
            response.then().statusCode(200);
            // Чек, что тело не пустое, привязываю определенный класс, дабы разорвать зависимость от предыдущих тестов
            ReceiptService.PositiveTests.Pojo.Responses.t1_3_checkMinLimitsSearch.Root root;
            root = response.then().extract().as(ReceiptService.PositiveTests.Pojo.Responses.t1_3_checkMinLimitsSearch.Root.class);
            assertNotNull(root.getResults());
            System.out.println("Тело запроса не пустое! Список результатов: " + root.getResults().toString());
            System.out.println("Тест №1_3 прошел успешно!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Step
    @Test
    @DisplayName("Проверка, что некорректный запрос вернет 0 результатов")
    @Description("Если например минимальное количество калорий = 1000, а максимальное = 1")
    public void t1_4_checkFailedSearch() {
        try {
            ReceiptBaseSettings receiptBaseSettings = new ReceiptBaseSettings();
            Map<String, Object> params = t1_4_checkFailedSearch.getParameters();

            Response response = given()
                    .spec(receiptBaseSettings.getSpec())
                    .queryParams(params)
                    .get("recipes/complexSearch");
            response.then().statusCode(200);
            // Начинаю проверку пришедшего ответа
            ReceiptService.PositiveTests.Pojo.Responses.t1_4_checkFailedSearch.Root root = response.then().extract().as(ReceiptService.PositiveTests.Pojo.Responses.t1_4_checkFailedSearch.Root.class);
            int expectNumbers = 1;
            int expectTotalResults = 0;
            String expectedArray = "[]";
            // Проверка что numbers = 1
            assertEquals("Поле numbers не 1!",expectNumbers,root.getNumber());
            // Проверка что totalResults = 0
            assertEquals("Поля totalResults не равны !", expectTotalResults, root.getTotalResults());
            // Удостоверка что массив пуст
            for (ReceiptService.PositiveTests.Pojo.Responses.t1_4_checkFailedSearch.Result result: root.getResults()) {
                assertNull(result.toString());
                System.out.println("Тест №1_4 прошел успешно! Массив пуст" + root.getResults().toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}

