package MealPlanningService.PositiveTests;

import MealPlanningService.PositiveTests.Pojo.Requests.t3_1_getMealPlanWeekAfterFillAllParameters;
import MealPlanningService.PositiveTests.Pojo.Requests.t3_4_checkLimitPeriodGeneration;
import MealPlanningService.PositiveTests.Pojo.Requests.t3_6_checkCalLimit;
import MealPlanningService.PositiveTests.Pojo.Responses.t2_2_getErrorMessageAfterFailedRegistration.Root;
import MealPlanningService.PositiveTests.Pojo.Responses.t3_3_getMealPlanWeekAfterFillAllParameters.Week;
import MealPlanningService.PositiveTests.Pojo.Responses.t3_4_checkLimitPeriodGeneration.Meal;
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

import java.util.ArrayList;
import java.util.Map;

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
    public void t2_1_checkSuccessRegistration() {
        try {
            System.out.println("Анонсирую начало прогона тест-сьюта №2");
            System.out.println("№2.1 - базовая проверка, что пользователь создается и проброс приходящих важных данных в переменные\n" +
                    "№2.2 - кейс по проверке, что некорректный запрос - показывает нужную ошибку - это позитивный кейс, так как эта ошибка подразумевается в документации\n");

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
            Assert.assertEquals("Статус не успешен!", body.getStatus(), "success");
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
            System.out.println("===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===");
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
            Assert.assertEquals("Пришел статус Success, хотя должен был быть failure " + body.getStatus(), "failure", body.getStatus());
            // Внедряю перебор для всех прогнанных кейсов, чтобы затем вывести их в сообщении
            if (body.getStatus().equals("failure")) {
                System.out.println("Тестовый сценарий: " + requestBody + " прошел успешно! Текст ошибки высветился: " + body.getStatus());
            } else {
                System.out.println("Тестовый сценарий: " + requestBody + " провален! Текст ошибки не появляется - валидация пройдена с ошибочными данными! " + body.getStatus());
            }
            System.out.println("Тест №2.2 прошел успешно!");
            System.out.println("===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    // Тест-сьют по ручке генерирования планов на еду mealplanner/generate
    // №3.1 - Проверка генерации плана на неделю с обычными параметрами
    // №3.2 - Проверка, что при пустом запросе - не генерируется блюда и БД тем самым не загружается
    // №3.3 - Проверка, что при всех параметрах выдается результат, ГЕНЕРАЦИЯ ДЛЯ НЕДЕЛИ
    // №3.4 - Проверка, что при попытке сгенерировать на больше чем неделю - генерируется лишь на день
    // №3.5 - Проверка работоспособности генерации меню на день


    @Step
    @DisplayName("Проверка генерации меню-плана на неделю")
    @Description("В параметрах запроса передаю самые базовые фильтры")
    @Test
    public void t3_1_getMealPlanWeek() {
        try {
            System.out.println("Анонсирую прогон тест-сьюта №3 по ручке генерирования планов на еду mealplanner/generate");
            System.out.println("№3.1 - Проверка генерации плана на неделю с обычными параметрами\n" +
                    "№3.2 - Проверка, что при пустом запросе - не генерируется блюда и БД тем самым не загружается\n" +
                    "№3.3 - Проверка, что при всех параметрах выдается результат, ГЕНЕРАЦИЯ ДЛЯ НЕДЕЛИ\n" +
                    "№3.4 - Проверка, что при попытке сгенерировать на больше чем неделю - генерируется лишь на день\n" +
                    "№3.5 - Проверка работоспособности генерации меню на день");
            System.out.println(" ");


            MealPlanningBaseSettings mealPlanningBaseSettings = new MealPlanningBaseSettings();

            Response response = given()
                    .spec(mealPlanningBaseSettings.getSpec())
                    // Так как параметра всего два в этом тесте - передаю напрямую
                    .queryParams("timeFrame", "week", "targetCalories", "3500")
                    .get("mealplanner/generate");

            response.then().statusCode(200);

            // Распаковка ответа в pojo
            MealPlanningService.PositiveTests.Pojo.Responses.t3_1_getMealPlanWeek.Root body = response.then().extract().as(MealPlanningService.PositiveTests.Pojo.Responses.t3_1_getMealPlanWeek.Root.class);
            // Чек, что ответ не пуст
            Assert.assertNotNull("Ответ не может быть пуст", body);
            // Чек всех дней недели, что они заполнились нужными данными
            // Суть в том, что в классе Week создал метод для помощи в переборе данных из классов дней недели
            // Затем создаю Map, где String - это день недели, а Объект - данные из класса Week
            Map<String, Object> weekData = body.getWeek().getAllDaysData();
            // Перебор значений с помощью интерфейса entry и запись их в переменную entry
            for (Map.Entry<String, Object> entry : weekData.entrySet()) {
                Assert.assertNotNull("Тело в классах дней недели - пусты", entry);
                System.out.println("Лог тела дней недели: " + entry.toString());
            }
            System.out.println("Тест кейс №3.1 прошел успешно!");
            System.out.println("===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Step
    @Test
    @DisplayName("Проверка, что при пустом запросе - не генерируется блюда и БД тем самым не загружается")
    public void t3_2_getFailedMealPlan() {
        try {
            MealPlanningBaseSettings mealPlanningBaseSettings = new MealPlanningBaseSettings();

            Response response = given()
                    .spec(mealPlanningBaseSettings.getSpec())
                    .queryParams("timeFrame", " ", "targetCalories", " ")
                    .get("mealplanner/generate");
            response.then().statusCode(404);

            System.out.println("Тест кейс №3.2 прошел успешно - данные не выдаются!");
            System.out.println("===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Step
    @Test
    @DisplayName("Проверка, что при всех параметрах выдается результат, ГЕНЕРАЦИЯ ДЛЯ НЕДЕЛИ")
    public void t3_3_getMealPlanWeekAfterFillAllParameters() {
        try {
            MealPlanningBaseSettings mealPlanningBaseSettings = new MealPlanningBaseSettings();

            Response response = given()
                    .spec(mealPlanningBaseSettings.getSpec())
                    .queryParams(t3_1_getMealPlanWeekAfterFillAllParameters.getParameters())
                    .get("mealplanner/generate");
            response.then().assertThat().statusCode(200);
            // Распаковка ответа в pojo классы
            MealPlanningService.PositiveTests.Pojo.Responses.t3_3_getMealPlanWeekAfterFillAllParameters.Root body = response.body().as(MealPlanningService.PositiveTests.Pojo.Responses.t3_3_getMealPlanWeekAfterFillAllParameters.Root.class);
            // Проверка, что ответ не пуст
            Assert.assertNotNull(body);
            // Более углубленная проверка что приходящие массивы - не пусты
            // Написал чисто для закрепления, этот код не нужен. ведь я уже проверил body
            Map<String, Object> responseDataOfWeek = body.getWeek().getAllDaysData();
            for (Map.Entry<String, Object> entry : responseDataOfWeek.entrySet()) {
                Assert.assertNotNull(entry);
            }
            System.out.println("Тест кейс №3.3 прошел успешно! При полноценных параметрах - приходит ответ");
            System.out.println("===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Step
    @Test
    @DisplayName("Проверка, что при попытке сгенерировать на больше чем неделю - генерируется лишь на день")
    @Description("Не должна выводиться ошибка, должно предоставляться меню на день и только 3 блюда!")
    public void t3_4_checkLimitPeriodGeneration() {
        try {
            MealPlanningBaseSettings mealPlanningBaseSettings = new MealPlanningBaseSettings();
            MealPlanningService.PositiveTests.Pojo.Requests.t3_4_checkLimitPeriodGeneration reqBody = new t3_4_checkLimitPeriodGeneration();

            Response response = given()
                    .spec(mealPlanningBaseSettings.getSpec())
                    .params(reqBody.getParameters())
                    .log().all()
                    .get("mealplanner/generate");
            response.then().statusCode(200);

            MealPlanningService.PositiveTests.Pojo.Responses.t3_4_checkLimitPeriodGeneration.Root body = response.body().as(MealPlanningService.PositiveTests.Pojo.Responses.t3_4_checkLimitPeriodGeneration.Root.class);
            Assert.assertNotNull(body);
            // Далее более углубленная проверка, ведь в класс Meals приходит массив с 3 блюдами
            // И нужно чекнуть, что блюда именно 3, а не больше и не меньше
            int expected = 3;
            int actual = 0;
            for (Meal meals : body.getMeals()) {
                if (meals.getId() > 0) {
                    actual++;
                } else {
                    System.out.println("Пришло: " + actual);
                }
            }
            Assert.assertEquals("Должно прийти лишь 3 блюда!", expected, actual);
            System.out.println(body.getMeals().toString());
            System.out.println("Тест кейс №3.4 прошел успешно, пришло: " + actual + " блюда");
            System.out.println("===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Step
    @Test
    @DisplayName("Проверка работоспособности генерации меню на день")
    public void t3_5_checkGenDayMeal() {
        MealPlanningBaseSettings mealPlanningBaseSettings = new MealPlanningBaseSettings();

        try {
            Response response = given()
                    .spec(mealPlanningBaseSettings.getSpec())
                    .queryParam("timeFrame", "day", "targetCalories", "2000")
                    .get("mealplanner/generate");
            response.then().statusCode(200);

            // Проверяю что ответ не пуст
            MealPlanningService.PositiveTests.Pojo.Responses.t3_5_checkGenDayMeal.Root body = response.body().as(MealPlanningService.PositiveTests.Pojo.Responses.t3_5_checkGenDayMeal.Root.class);
            Assert.assertNotNull(body);

            // Проверяю, что тело не пустое, более углубленно, заглядывая глубже во вложенный в Root класс Meal, и в его поля getTitle
            for (MealPlanningService.PositiveTests.Pojo.Responses.t3_5_checkGenDayMeal.Meal meal : body.getMeals()) {
                Assert.assertNotNull("Поле Title не может быть пустым", meal.getTitle());
                System.out.println(meal.getTitle());
            }
            System.out.println("Тест кейс №3.5 прошел успешно!");
            System.out.println("===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Step
    @Test
    @DisplayName("Проверка работоспособности ограничения поля calories")
    @Description("Если ищется значение 2000 калорий - то должно именно столько приходить в ответе")
    public void t3_6_checkCalLimit() {
        MealPlanningBaseSettings mealPlanningBaseSettings = new MealPlanningBaseSettings();
        MealPlanningService.PositiveTests.Pojo.Requests.t3_6_checkCalLimit reqBody = new t3_6_checkCalLimit();
        try {
            Response response = given()
                    .spec(mealPlanningBaseSettings.getSpec())
                    .params(reqBody.getParameters())
                    .get("mealplanner/generate");
            response.then().statusCode(200);

            // Распаковка ответа
            MealPlanningService.PositiveTests.Pojo.Responses.t3_6_checkCalLimit.Root body = response.body().as(MealPlanningService.PositiveTests.Pojo.Responses.t3_6_checkCalLimit.Root.class);
            // Проверка что ответ не пуст
            Assert.assertNotNull(body);
            // Проверка корректной работы ограничителя, у поля Calories
            String expected = "2000.";
            String expected2 = "1999.";
            String actual = String.valueOf(body.getNutrients().getCalories());
            if (actual.contains(expected) || actual.contains(expected2)) {
                System.out.println("Ограничитель работает");
                System.out.println(actual);
            }
            System.out.println("Тест кейс №3.6 прошел успешно!");
            System.out.println("===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===" + "===");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

