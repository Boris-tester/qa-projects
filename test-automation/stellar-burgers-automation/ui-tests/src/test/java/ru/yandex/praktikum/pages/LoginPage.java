package ru.yandex.praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private By inputByLabel(String labelText) {
        return By.xpath("//label[normalize-space()='" + labelText + "']/following-sibling::input");
    }

    private final By emailInput = inputByLabel("Email");
    private final By passwordInput = inputByLabel("Пароль");

    private final By loginButton = By.xpath("//button[normalize-space()='Войти']");
    private final By registerLink = By.xpath("//a[normalize-space()='Зарегистрироваться']");
    private final By forgotPasswordLink = By.xpath("//a[normalize-space()='Восстановить пароль']");
    private final By makeOrderButton = By.xpath("//button[normalize-space()='Оформить заказ']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Ввести email: {email}")
    public LoginPage setEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).clear();
        driver.findElement(emailInput).sendKeys(email);
        return this;
    }

    @Step("Ввести пароль")
    public LoginPage setPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).clear();
        driver.findElement(passwordInput).sendKeys(password);
        return this;
    }

    @Step("Нажать 'Войти'")
    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    @Step("Перейти по ссылке 'Зарегистрироваться'")
    public void clickRegisterLink() {
        wait.until(ExpectedConditions.elementToBeClickable(registerLink)).click();
    }

    @Step("Перейти по ссылке 'Восстановить пароль'")
    public void clickForgotPassword() {
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink)).click();
    }

    @Step("Проверить, что вход выполнен (видна кнопка 'Оформить заказ')")
    public boolean isLoggedIn() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(makeOrderButton)).isDisplayed();
    }
}
