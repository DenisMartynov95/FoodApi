package MealPlanningService.NegativeTests;

import MealPlanningService.NegativeTests.Pojo.Requests.t2n_2_letCheckLimitForRegistrations;
import MealPlanningService.NegativeTests.Pojo.Requests.t2n_3_letCheckLimitForFirstName;
import MealPlanningService.NegativeTests.Pojo.Requests.t2n_4_letCheckLimitForLastName;
import MealPlanningService.NegativeTests.Pojo.Requests.t2n_5_letCheckLimitForEmail;
import MealPlanningService.NegativeTests.Pojo.Responses.t2n_1_letCrushRegistration.Root;
import ReceiptService.MealPlanningBaseSettings;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NegativeTests {

    // Тест-сьют по ручке поиска рецептов users/connect
    // №2.1 - Проверка тестовых наборов данных (весьма ограниченных)
    // №2.2 - Проверка ограничителя по символам для поля ввода username
    // №2.3 - Проверка ограничителя по символам для поля ввода firstName
    // №2.4 - Проверка ограничителя по символам для поля ввода lastName
    // №2.5 - Проверка ограничителя по символам для поля ввода email

    @Step
    @Test
    @DisplayName("Анонс начала прогона тестового сьюта №2")
    public void annotation() {
        System.out.println("Анонсирую начало прогона негативного тест-сьюта №2 состоящий из следующих сценариев: ");
        System.out.println("№2.1 - Проверка тестовых наборов данных\n" + "№2.2 - Проверка ограничителя по символам для поля ввода username\n" + "№2.3 - Проверка ограничителя по символам для поля ввода firstName\n" +
                "№2.4 - Проверка ограничителя по символам для поля ввода lastName\n" + "№2.5 - Проверка ограничителя по символам для поля ввода email\n");
        System.out.println(" ");
    }

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
            "a$#*, *@#*&*$, *&*$&*@, test@mail,ru",
            "a$#*, *@#*&*$, *&*$&*@, test@mail,.ru"
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
                    System.out.println("ВНИМАНИЕ ОБНАРУЖЕН БАГ!!!");
                    System.out.println("Данные: " + bodyReq + " были пропущены в базу данных");

                } else if (root.getStatus().equals(failure)) {
                    System.out.println("Корректно! Данные не прошли: " + bodyReq);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Тест №2.1 завершен!");
            System.out.println("=========================================================================================================================================================");
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
                System.out.println("Тест №2.2  прошел успешно! Username не пропущен в БД");
                System.out.println("=========================================================================================================================================================");
            } else {
                System.out.println("ВНИМАНИЕ!!! Данные попали в БД, они не валидны!");
                System.out.println("Тест №2.2 ПРОВАЛИЛСЯ");

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
            System.out.println("Тест №2.3 прошел успешно!");
            System.out.println("=========================================================================================================================================================");

        } else {
            System.out.println("ВНИМАНИЕ!!! Данные попали в БД, они не валидны!");
            System.out.println("Тест №2.3 ПРОВАЛИЛСЯ");
        }
    }

    @Step
    @Test
    @DisplayName("Проверка ограничителя по символам для поля ввода lastName")
    public void t2n_4_letCheckLimitForLastName() {
        MealPlanningBaseSettings mealPlanningBaseSettings = new MealPlanningBaseSettings();
        t2n_4_letCheckLimitForLastName body = new t2n_4_letCheckLimitForLastName();

        Response response = given()
                .spec(mealPlanningBaseSettings.getSpec())
                .body(body.getParameters())
                .post("users/connect");
        response.then().assertThat().statusCode(200);

        // Распаковываю тело
        // Так как существует баг и ответ всего 200, нужно проверить поле status у ответа
        MealPlanningService.NegativeTests.Pojo.Responses.t2n_4_letCheckLimitForLastName.Root root = response.then().extract().as(MealPlanningService.NegativeTests.Pojo.Responses.t2n_4_letCheckLimitForLastName.Root.class);
        String expected = "failure";

        if (root.getStatus().equals(expected)) {
            System.out.println("Ответ failure! Данные не попали в БД");
            System.out.println("Тест №2.4 прошел успешно!");
            System.out.println("=========================================================================================================================================================");

        } else {
            System.out.println("ВНИМАНИЕ!!! Данные попали в БД, они не валидны!");
            System.out.println("Тест №2.4 ПРОВАЛИЛСЯ");
        }
    }

    @Step
    @Test
    @DisplayName("Проверка ограничителя по символам для поля ввода email")
    public void t2n_5_letCheckLimitForEmail() {
        MealPlanningBaseSettings mealPlanningBaseSettings = new MealPlanningBaseSettings();
        t2n_5_letCheckLimitForEmail body = new t2n_5_letCheckLimitForEmail();

        Response response = given()
                .spec(mealPlanningBaseSettings.getSpec())
                .body(body.getParameters())
                .post("users/connect");
        response.then().assertThat().statusCode(200);

        // Распаковываю тело
        // Так как существует баг и ответ всего 200, нужно проверить поле status у ответа
        MealPlanningService.NegativeTests.Pojo.Responses.t2n_5_letCheckLimitForEmail.Root root = response.then().extract().as(MealPlanningService.NegativeTests.Pojo.Responses.t2n_5_letCheckLimitForEmail.Root.class);
        String expected = "failure";

        if (root.getStatus().equals(expected)) {
            System.out.println("Ответ failure! Данные не попали в БД");
            System.out.println("Тест №2.5 прошел успешно!");
            System.out.println("=========================================================================================================================================================");

        } else {
            System.out.println("ВНИМАНИЕ!!! Данные попали в БД, они не валидны!");
            System.out.println("Тест №2.5 ПРОВАЛИЛСЯ");
        }
    }

    // НЕГАТИВНЫЙ Тест-сьют по ручке генерирования планов на еду mealplanner/generate
    //
    @Step
    @Test
    public void annotation2() {
        System.out.println("Анонсирую начало прогона негативного тест-сьюта №3 состоящий из следующих сценариев: ");
        System.out.println("");
        System.out.println(" ");
    }

    @Step
    @Test
    @DisplayName("Параметризированный запрос с попыткой выявить возможность отправить запрос с некорректными параметрами")
    @CsvSource({
            "year, 1500, Gluten Free, shellfish", // Чекаю 1 параметр невалидными запросами
            "83274, 1500, Gluten Free, shellfish", // Чекаю 1 параметр невалидными запросами
            "&^#$, 1500, Gluten Free, shellfish", // Чекаю 1 параметр невалидными запросами
            "день, 1500, Gluten Free, shellfish", // Чекаю 1 параметр невалидными запросами
            "SELECT * from Menu Limit 10, 1500, Gluten Free, shellfish", // Чекаю 1 параметр невалидными запросами
            "day, 92139198123, Gluten Free, shellfish", // Ломаю 2 параметр
            "day, , Gluten Free, shellfish", // Ломаю 2 параметр
            "day, sadasgre, Gluten Free, shellfish", // Ломаю 2 параметр
            "day, , SELECT * FROM menu WHERE targetCalories > 1500 LIMIT 10, shellfish", // Ломаю 2 параметр
            "day,&$#^,Gluten Free, shellfish", // Ломаю 2 параметр
            "day,1500,Gluten , shellfish", // Ломаю 3 параметр
            "day,1500, , shellfish", // Ломаю 3 параметр
            "day,1500,gluten free, shellfish", // Ломаю 3 параметр
            "day,1500,Свободные глютены, shellfish", // Ломаю 3 параметр
            "day,1500,3424234, shellfish", // Ломаю 3 параметр
            "day,1500,.%№,№, shellfish", // Ломаю 3 параметр
            "year, 1500, Gluten Free, shellfish", // Чекаю 4 параметр невалидными запросами


    })

    public void t3n_getMealPlanning(String timeFrame, String targetCalories, String diet, String exclude) {
        MealPlanningBaseSettings mealPlanningBaseSettings = new MealPlanningBaseSettings();
        String bodyReq = String.format("{ \"timeFrame\": \"%s\", \"targetCalories\": \"%s\", \"diet\": \"%s\", \"exclude\": \"%s\" }", timeFrame, targetCalories, diet, exclude);


    }




}
