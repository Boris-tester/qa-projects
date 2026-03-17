package ru.yandex.praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private By inputByLabel(String labelText) {
        return By.xpath("//label[normalize-space()='" + labelText + "']/following-sibling::input");
    }

    private final By nameInput = inputByLabel("Имя");
    private final By emailInput = inputByLabel("Email");
    private final By passwordInput = inputByLabel("Пароль");

    private final By registerButton = By.xpath("//button[normalize-space()='Зарегистрироваться']");
    private final By loginLink = By.xpath("//a[normalize-space()='Войти']");

    private final By passwordError =
            By.xpath("//p[contains(@class,'input__error') and contains(.,'Некорректный пароль')]");

    private final By loginTitle = By.xpath("//h2[normalize-space()='Вход']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Заполнить форму регистрации")
    public RegisterPage fillForm(String name, String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput)).clear();
        driver.findElement(nameInput).sendKeys(name);

        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);

        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);

        return this;
    }

    @Step("Нажать 'Зарегистрироваться'")
    public void clickRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }

    @Step("Нажать 'Войти' в форме регистрации")
    public void clickLoginLink() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
    }

    @Step("Проверить, что показана ошибка некорректного пароля")
    public boolean isPasswordErrorVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordError)).isDisplayed();
    }

    @Step("Проверить, что после регистрации открылась страница входа")
    public boolean isLoginPageOpened() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginTitle)).isDisplayed();
    }
}
