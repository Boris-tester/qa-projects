package ru.yandex.praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConstructorPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ===== Локаторы =====

    private By tabByName(String tabName) {
        return By.xpath("//div[contains(@class,'tab_tab')][.//span[normalize-space()='" + tabName + "']]");
    }

    private By sectionTitleByName(String sectionName) {
        return By.xpath("//h2[normalize-space()='" + sectionName + "']");
    }

    // ===== Действия =====

    @Step("Перейти на вкладку 'Булки'")
    public void openBuns() {
        openTabAndWaitSection("Булки");
    }

    @Step("Перейти на вкладку 'Соусы'")
    public void openSauces() {
        openTabAndWaitSection("Соусы");
    }

    @Step("Перейти на вкладку 'Начинки'")
    public void openFillings() {
        openTabAndWaitSection("Начинки");
    }

    private void openTabAndWaitSection(String name) {
        By tab = tabByName(name);
        By sectionTitle = sectionTitleByName(name);

        clickTab(tab);
        wait.until(ExpectedConditions.visibilityOfElementLocated(sectionTitle));
        wait.until(driver -> isTabActive(name));
    }

    private void clickTab(By tab) {
        // 1. Ждём, что вкладка реально видима
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(tab));

        // 2. Скроллим к элементу
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", el);

        // 3. Ждём кликабельность по локатору
        wait.until(ExpectedConditions.elementToBeClickable(tab));

        // 4. Кликаем
        try {
            driver.findElement(tab).click();
        } catch (Exception e) {
            WebElement el2 = driver.findElement(tab);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el2);
        }
    }

    // ===== Проверки (через class активной вкладки) =====

    @Step("Проверить, что активна вкладка 'Булки'")
    public boolean isBunsActive() {
        return isTabActive("Булки");
    }

    @Step("Проверить, что активна вкладка 'Соусы'")
    public boolean isSaucesActive() {
        return isTabActive("Соусы");
    }

    @Step("Проверить, что активна вкладка 'Начинки'")
    public boolean isFillingsActive() {
        return isTabActive("Начинки");
    }

    private boolean isTabActive(String tabName) {
        By tabLocator = tabByName(tabName);

        return wait.until(driver -> {
            WebElement tab = driver.findElement(tabLocator);
            String classAttr = tab.getAttribute("class");
            return classAttr != null && classAttr.contains("tab_tab_type_current");
        });
    }
}
