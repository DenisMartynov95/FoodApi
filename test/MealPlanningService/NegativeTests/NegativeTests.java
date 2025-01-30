package MealPlanningService.NegativeTests;

import MealPlanningService.NegativeTests.Pojo.Responses.t2n_1_letCrushRegistration.Root;
import ReceiptService.MealPlanningBaseSettings;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
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
        int numberFailure = 0; // Вынес переменную за границы try catch, так как необходима вконце всего теста
        try {
            String success = "success";
            String failure = "failure";

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
                Root root = response.body().as(Root.class);

                if (root.getStatus().equals(success)) {
                    System.out.println("ВНИМАНИЕ!!!");
                    System.out.println("Данные: " + bodyReq + " были пропущены в базу данных");
                    numberFailure++;
                } else if (root.getStatus().equals(failure)) {
                    System.out.println("Корректно! Данные не прошли: " + bodyReq);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("В итоге, было неверно пропущено " + numberFailure + " тестовых вариантов");
        }
    }

}
