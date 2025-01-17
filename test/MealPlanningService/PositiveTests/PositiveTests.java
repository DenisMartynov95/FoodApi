package MealPlanningService.PositiveTests;

import MealPlanningService.PositiveTests.Pojo.Responses.t2_2_getErrorMessageAfterFailedRegistration.Root;
import MealPlanningService.PositiveTests.Pojo.Responses.t3_1_getMealPlanWeek.*;
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

import java.util.Arrays;

import static MealPlanningService.PositiveTests.ImportantData.SavedData.*;
import static io.restassured.RestAssured.given;

public class PositiveTests {


    // Тест-сьют по ручке поиска рецептов users/connect
    // №2.1 - базовая проверка, что пользователь создается и проброс приходящих важных данных в переменные
    // №2.2 - кейс по проверке, что некорректный запрос - показывает нужную ошибку - это позитивный кейс, так как эта ошибка подразумевается в документации

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
            // Проверяю что поля не пусты, а потом перекладываю эти данные на хранение в переменные SavedData
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
            "#, null, null, null",        // Символ # в username
            "#, ##, null, null",          // Символ # в username, ## в firstName
            "#, ##, ###, null",           // Несколько символов #
            "#, #@, #@#, #^$#",           // Комбинация символов
    })
    public void t2_2_getErrorMessageAfterFailedRegistration(String username, String firstName, String lastName, String email) {
        try {
            MealPlanningBaseSettings mealPlanningBaseSettings = new MealPlanningBaseSettings();

            // Генерируем RAW JSON
            String requestBody = String.format( // Так как мне нужно передать просто # без СКОБОЧЕК - то я и убрал скобки из %s , если бы нужно было передать строку - то было бы "%s"
                    "{ \"username\": %s, \"firstName\": %s, \"lastName\": %s, \"email\": %s }",
                    username, firstName, lastName, email
            );

            Response response = given()
                    .spec(mealPlanningBaseSettings.getSpec())
                    .body(requestBody)
                    .log().all()
                    .post("users/connect");

            // Проверяем статус ответа
            response.then().statusCode(400);
            // Распаковываю приходящий ответ в pojo
            Root body = response.body().as(Root.class);
            // Проверка статуса для всех 4 тестов
            Assert.assertEquals("Пришел статус Success, хотя должен был быть failure " + body.getStatus(),"failure",body.getStatus());
            // Внедряю перебор для всех прогнанных кейсов, чтобы затем вывести их в сообщении
            if (body.getStatus().equals("failure")) {
                System.out.println("Тестовый сценарий: " + requestBody + " прошел успешно! Текст ошибки высветился: " + body.getStatus());
            } else {
                System.out.println("Тестовый сценарий: " + requestBody + " провален! Текст ошибки не появляется - валидация пройдена с ошибочными данными! " + body.getStatus());
            }
            System.out.println("Тест №2.2 прошел успешно!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    // Тест-сьют по ручке генерирования планов на еду mealplanner/generate
    // №3.1 - Проверка генерации плана на неделю с обычными параметрами
    @Step
    @DisplayName("Проверка генерации меню-плана на неделю")
    @Description("В параметрах запроса передаю самые базовые фильтры")
    @Test
    public void t3_1_getMealPlanWeek() {
        try {
            MealPlanningBaseSettings mealPlanningBaseSettings = new MealPlanningBaseSettings();

            Response response = given()
                    .spec(mealPlanningBaseSettings.getSpec())
                    // Так как параметра всего два в этом тесте - передаю напрямую
                    .queryParams("timeFrame","week","targetCalories","3500")
                    .get("mealplanner/generate");

            response.then().statusCode(200);

            // Распаковка ответа в pojo
            MealPlanningService.PositiveTests.Pojo.Responses.t3_1_getMealPlanWeek.Root body = response.then().extract().as(MealPlanningService.PositiveTests.Pojo.Responses.t3_1_getMealPlanWeek.Root.class);
            // Чек, что ответ не пуст
            Assert.assertNotNull("Ответ не может быть пуст",body);
            // Чек всех дней недели, что они заполнились нужными данными

//            String monday = body.getWeek().getMonday().toString();
//            System.out.println(monday);
            for (Root root : body.getWeek().getAllDaysData()) {

            }

//            Monday monday = new Monday();
//            Assert.assertNotNull("Понедельник пуст",monday.getMeals());
//            System.out.println("Понедельник: " + monday.getMeals());
//            Tuesday tuesday = new Tuesday();
//            Assert.assertNotNull("Вторник пуст",tuesday.getMeals());
//            System.out.println("Вторник: " + tuesday.getMeals());
//            Wednesday wednesday = new Wednesday();
//            Assert.assertNotNull("Среда пуста",wednesday.getMeals());
//            System.out.println("Среда: " + wednesday.getMeals());
//            Thursday thursday = new Thursday();
//            Assert.assertNotNull("Четверг пуст",thursday.getMeals());
//            System.out.println("Четверг: " + thursday.getMeals());
//            Friday friday = new Friday();
//            Assert.assertNotNull("Пятница пуста",friday.getMeals());
//            System.out.println("Пятница: " + friday.getMeals());
//            Saturday saturday = new Saturday();
//            Assert.assertNotNull("Суббота пуста",saturday.getMeals());
//            System.out.println("Суббота: " + saturday.getMeals());
//            Sunday sunday = new Sunday();
//            Assert.assertNotNull("Воскресенье пуст",sunday.getMeals());
//            System.out.println("Воскресенье: " + sunday.getMeals());


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }




}

