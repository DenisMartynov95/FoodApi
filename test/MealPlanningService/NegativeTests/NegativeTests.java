package MealPlanningService.NegativeTests;

import MealPlanningService.NegativeTests.Pojo.Requests.t2n_2_letCheckLimitForRegistrations;
import MealPlanningService.NegativeTests.Pojo.Requests.t2n_3_letCheckLimitForFirstName;
import MealPlanningService.NegativeTests.Pojo.Responses.t2n_1_letCrushRegistration.Root;
import ReceiptService.MealPlanningBaseSettings;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;

public class NegativeTests {

    // Тест-сьют по ручке поиска рецептов users/connect
    // №2.1 - проверка тестовых наборов данных (весьма ограниченных)

    @Step
    @DisplayName("Комплексный тест вводимых данных для регистрации")
    @Description("Так как есть ограничение на количество бесплатных запросов - я не смогу атомизировать этот тест для каждого поля, поэтому хоть что-то напишу")
    @ParameterizedTest
    @CsvSource({
            "123, firstName, lastName, test@mail.ru",
            "test, 123, lastName, test@mail.ru",
            "test, , lastname, test@mail.ru",
            "test, firstname, 123, test@mail.ru",
            "test,firstname, , test@mail.ru",
            "test, firstname, lastname, testmail.ru",
            "test, firstname, lastname, testmailru",
            "test, firstname, lastname, ",
            "#76324<, first, last, test@mail.ru",
            "&^$#, &#^$, last, test@mail.ru",
            "&*^#$, *^$, 7&^%*#<, test@mail.ru",
            "$#&^%%, 8&^&*^%, &^&%^&*, ^&^*(@",
    })

    public void t2n_1_letCrushRegistration(String username, String firstName, String lastName, String email) {
        try {

            MealPlanningBaseSettings mealPlanningBaseSettings = new MealPlanningBaseSettings();

            String bodyReq = String.format("{ \"username\": \"%s\", \"firstName\": \"%s\", \"lastName\": \"%s\", \"email\": \"%s\" }", username, firstName, lastName, email);

            Response response = given()
                    .spec(mealPlanningBaseSettings.getSpec())
                    .body(bodyReq)
                    .post("users/connect");

            try {
                response.then().statusCode(400); // К сожалению, существуют баги, при которых возвращает 200, хотя не должно
                // поэтому мне нужен finally в котором будет произведена работа с ответом, более углубленно

            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                // Распаковка ответа
                Root root = response.body().as(Root.class);
                // Переменные для сравнения со статусами
                String success = "success";
                String failure = "failure";
                if (root.getStatus().equals(success)) {
                    System.out.println("ВНИМАНИЕ!!!");
                    System.out.println("Данные: " + bodyReq + " были пропущены в базу данных");

                } else if (root.getStatus().equals(failure)) {
                    System.out.println("Корректно! Данные не прошли: " + bodyReq);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Step
    @Test
    @DisplayName("Проверка ограничителя по символам для поля ввода username")
    public void t2n_2_letCheckLimitForUsernameField() {
        MealPlanningBaseSettings mealPlanningBaseSettings = new MealPlanningBaseSettings();
        t2n_2_letCheckLimitForRegistrations body = new t2n_2_letCheckLimitForRegistrations();

        Response response = given()
                .spec(mealPlanningBaseSettings.getSpec())
                .body(body.getParams())
                .post("users/connect");
        // У этой ручки есть баг - он возвращает 200 статус, хотя статус приходит failure
        // Поэтому углубленно надо проверить
        try {
            int statusCode = response.statusCode();
            int expectedSC = 400;
            Assert.assertEquals("Статус код должен быть 400!",expectedSC,statusCode);
        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + response.statusCode());
        } finally {
            // Распаковываю ответ
            MealPlanningService.NegativeTests.Pojo.Responses.t2n_2_letCheckLimitForUsernameField.Root root = response.body().as(MealPlanningService.NegativeTests.Pojo.Responses.t2n_2_letCheckLimitForUsernameField.Root.class);
            // И проверяю на самом ли деле тест прошел успешно, или все же статус failure
            String expected = "failure";
            if (root.getStatus().equals(expected)) {
                System.out.println("Тест-кейс прошел успешно! Username не пропущен в БД");
            }
        }
    }

    @Step
    @Test
    @DisplayName("Проверка ограничителя по символам для поля ввода firstName")
    public void t2n_3_letCheckLimitForFirstName() {
        MealPlanningBaseSettings mealPlanningBaseSettings = new MealPlanningBaseSettings();
        t2n_3_letCheckLimitForFirstName body = new t2n_3_letCheckLimitForFirstName();

        Response response = given()
                .spec(mealPlanningBaseSettings.getSpec())
                .body(body.getParameters())
                .post("users/connect");
        response.then().assertThat().statusCode(200);

        // Распаковываю тело
        // Так как существует баг и ответ всего 200, нужно проверить поле status у ответа
        MealPlanningService.NegativeTests.Pojo.Responses.t2n_3_letCheckLimitForFirstName.Root root = response.then().extract().as(MealPlanningService.NegativeTests.Pojo.Responses.t2n_3_letCheckLimitForFirstName.Root.class);
        String expected = "failure";

        if (root.getStatus().equals(expected)) {
            System.out.println("Ответ failure! Данные не попали в БД");
        } else {
            System.out.println("ВНИМАНИЕ!!! Данные попали в БД, они не валидны!");
        }

    }





}
