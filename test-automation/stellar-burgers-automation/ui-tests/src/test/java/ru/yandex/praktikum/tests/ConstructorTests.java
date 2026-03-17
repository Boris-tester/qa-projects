package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;
import ru.yandex.praktikum.base.BaseUiTest;
import ru.yandex.praktikum.pages.ConstructorPage;
import ru.yandex.praktikum.pages.MainPage;

public class ConstructorTests extends BaseUiTest {

    @Test
    @DisplayName("Переход на вкладку Булки")
    @Description("Проверка переключения вкладок в конструкторе: после перехода с Соусов на Булки вкладка Булки становится активной")
    public void shouldSwitchToBunsTest() {
        new MainPage(driver).open(baseUrl);

        ConstructorPage constructor = new ConstructorPage(driver);

        constructor.openSauces();
        Assert.assertTrue("Вкладка 'Соусы' должна стать активной",
                constructor.isSaucesActive());

        constructor.openBuns();
        Assert.assertTrue("Вкладка 'Булки' должна стать активной",
                constructor.isBunsActive());
    }

    @Test
    @DisplayName("Переход на вкладку Соусы")
    @Description("Проверка, что при выборе вкладки Соусы она становится активной")
    public void shouldSwitchToSaucesTest() {
        new MainPage(driver).open(baseUrl);

        ConstructorPage constructor = new ConstructorPage(driver);
        constructor.openSauces();

        Assert.assertTrue("Вкладка 'Соусы' должна стать активной",
                constructor.isSaucesActive());
    }

    @Test
    @DisplayName("Переход на вкладку Начинки")
    @Description("Проверка, что при выборе вкладки Начинки она становится активной")
    public void shouldSwitchToFillingsTest() {
        new MainPage(driver).open(baseUrl);

        ConstructorPage constructor = new ConstructorPage(driver);
        constructor.openFillings();

        Assert.assertTrue("Вкладка 'Начинки' должна стать активной",
                constructor.isFillingsActive());
    }
}
