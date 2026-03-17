package ru.yandex.praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By loginToAccountButton =
            By.xpath("//button[normalize-space()='Войти в аккаунт']");

    // более устойчиво: ищем ссылку, внутри которой есть текст "Личный Кабинет"
    private final By personalAccountButton =
            By.xpath("//a[.//p[normalize-space()='Личный Кабинет']]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    @Step("Открыть главную страницу")
    public MainPage open(String baseUrl) {
        driver.get(baseUrl);
        return this;
    }

    @Step("Нажать 'Войти в аккаунт' на главной")
    public void clickLoginToAccount() {
        click(loginToAccountButton);
    }

    @Step("Нажать 'Личный кабинет' на главной")
    public void clickPersonalAccount() {
        click(personalAccountButton);
    }

    private void click(By locator) {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", el
        );

        wait.until(ExpectedConditions.elementToBeClickable(el));

        try {
            el.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }
}
