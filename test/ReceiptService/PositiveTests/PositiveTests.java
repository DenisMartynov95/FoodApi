package ReceiptService.PositiveTests;

import ReceiptService.ReceiptBaseSettings;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

public class PositiveTests {

    @Before
    public void setUp() {
        ReceiptBaseSettings receiptSpec = new ReceiptBaseSettings();
    }

    // Тест-сьют по ручке поиска рецептов
    // №1 - базовая проверка, что поиск работает и возвращает данные
    // №2 - № 3 - проверка ограничителя выдачи результатов (от 1 до 100)
    @Step
    @Test
    @DisplayName("Работоспособность поиск на сервисе")
    public void t1_1_checkSearch() {
        try {

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
