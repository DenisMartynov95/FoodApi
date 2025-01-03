package MealPlanningService.PositiveTests;

import ReceiptService.MealPlanningBaseSettings;
import MealPlanningService.PositiveTests.Pojo.Requests.t2_1_checkSuccessRegistration;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static MealPlanningService.PositiveTests.ImportantData.SavedData.*;
import static io.restassured.RestAssured.given;

public class PositiveTests {


    // Тест-сьют по ручке поиска рецептов users/connect
    // №2.1 - базовая проверка, что пользователь создается и проброс приходящих важных данных в переменные

    @Step
    @Test
    @DisplayName("Проверка создания пользователя и получение от него важных данных")
    @Description("Проверка, что пользователь вообще создается и данные приходят")
    public void t2_1_checkSuccessRegistration(){
        try {
            MealPlanningBaseSettings mealPlanningBaseSettings = new MealPlanningBaseSettings();
            // Создаю объект класса, содержащий данные, которые я хочу передать
            t2_1_checkSuccessRegistration requestBody = new t2_1_checkSuccessRegistration();

            Response response = given()
                    .spec(mealPlanningBaseSettings.getSpec())
                    .body(requestBody)
                    .post("users/connect");
            response.then().statusCode(200);

            // Распаковываю ответ и манипулирую его данными
            MealPlanningService.PositiveTests.Pojo.Responses.t2_1_checkSuccessRegistration.Root body = response.then().extract().as(MealPlanningService.PositiveTests.Pojo.Responses.t2_1_checkSuccessRegistration.Root.class);
            // Проверяю что поля не пусты, а потом перекладываю эти данные на хранения в переменные SavedData
            Assert.assertEquals("Статус не успешен!", body.getStatus(),"success");
            Assert.assertNotNull("userName пуст!", body.getUsername());
            Assert.assertNotNull("spoonacularPassword пуст!", body.getSpoonacularPassword());
            Assert.assertNotNull("hash пуст!", body.getHash());

            // Перекладываю важные данные в класс SavedData
            username = body.getHash();
            spoonacularPassword = body.getSpoonacularPassword();
            hash = body.getHash();

            // Вывожу сообщение об успехе
            System.out.println("Тест №2_1 прошел успешно! Данные приняты");
            System.out.println(body.getHash() + " " + body.getUsername() + " " + body.getSpoonacularPassword() + " " + body.getStatus());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Step
    @DisplayName("Проверка статуса failure и что данные не выдаются")
    @Description("Так как на ручке имеются баги, можно зарегать пустые строки - буду подкладывать абсолютно некорректный тип данных")
    @ParameterizedTest
    @CsvSource({
            "'', '', '', ''",      // Просто пустые строки - ДОЛЖЕН ПРОЙТИ ТЕСТ ЗДЕСЬ
            "#, '', '', ''",       // Некорректный тип в первом параметре -  УЖЕ НЕ ДОЛЖНЫ НАЧАТЬ ПРОХОДИТЬ
            "#, ##, '', ''",       // Некорректный тип в первом и втором параметрах
            "#, ##,###, ''",
            "#, #@,#@#,#^$#",
    })
    @Test
    public void t2_1_getErrorMessageAfterFailedRegistration(String username, String firstName, String lastName, String email) {
        try {
            MealPlanningBaseSettings mealPlanningBaseSettings = new MealPlanningBaseSettings();
            String requestBody = String.format(
                    "{ \"username\": \"%s\", \"firstName\": \"%s\", \"lastName\": \"%s\", \"email\": \"%s\" }",
                    username, firstName, lastName, email
            );

            Response response = given()
                    .spec(mealPlanningBaseSettings.getSpec())
                    .body(requestBody)
                    .post("users/connect");




        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
    }

}

