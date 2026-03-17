package ru.yandex.praktikum.config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class RestAssuredConfig {

    public static final String BASE_URL = "https://stellarburgers.education-services.ru";

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.filters(new AllureRestAssured());
    }
}
