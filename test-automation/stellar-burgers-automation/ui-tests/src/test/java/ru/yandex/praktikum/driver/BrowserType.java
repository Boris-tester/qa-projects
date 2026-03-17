package ru.yandex.praktikum.driver;

public enum BrowserType {
    CHROME,
    YANDEX;

    public static BrowserType from(String value) {
        if (value == null) return CHROME;
        switch (value.toLowerCase()) {
            case "yandex":
                return YANDEX;
            default:
                return CHROME;
        }
    }
}
