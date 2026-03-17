package ru.yandex.praktikum.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    public WebDriver create() {

        BrowserType type = BrowserType.from(System.getProperty("browser", "chrome"));

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1280,900");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        if (type == BrowserType.YANDEX) {

            String yandexBinary = System.getProperty("yandex.binary");
            String yandexDriver = System.getProperty("yandex.driver");

            if (yandexBinary == null || yandexBinary.isBlank()
                    || yandexDriver == null || yandexDriver.isBlank()) {

                throw new IllegalArgumentException(
                        "Для запуска Yandex Browser укажи параметры:\n" +
                                "-Dbrowser=yandex " +
                                "-Dyandex.binary=\"...\\browser.exe\" " +
                                "-Dyandex.driver=\"...\\yandexdriver.exe\""
                );
            }

            // путь к драйверу
            System.setProperty("webdriver.chrome.driver", yandexDriver);

            // путь к самому браузеру
            options.setBinary(yandexBinary);
        }

        return new ChromeDriver(options);
    }
}
