package ru.yandex.praktikum.base;

import io.qameta.allure.Attachment;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.driver.DriverFactory;

import java.time.Duration;

public abstract class BaseUiTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String baseUrl;

    @Rule
    public TestWatcher watcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            attachScreenshot();
            attachPageSource();
        }
    };

    @Before
    public void setUp() {
        baseUrl = System.getProperty("base.url", "https://stellarburgers.education-services.ru/");
        driver = new DriverFactory().create();

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.manage().deleteAllCookies();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] attachScreenshot() {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            return new byte[0];
        }
    }

    @Attachment(value = "Page source", type = "text/html")
    public byte[] attachPageSource() {
        try {
            return driver.getPageSource().getBytes();
        } catch (Exception e) {
            return new byte[0];
        }
    }
}
