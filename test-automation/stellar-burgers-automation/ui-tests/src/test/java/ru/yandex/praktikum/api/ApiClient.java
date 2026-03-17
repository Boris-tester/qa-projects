package ru.yandex.praktikum.api;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiClient {

    private final String apiUrl;

    public ApiClient() {
        // из pom пробрасывается api.url, дефолт в pom тоже есть
        this.apiUrl = System.getProperty("api.url", "https://stellarburgers.education-services.ru/");
    }

    public RequestSpecification spec() {
        return given()
                .baseUri(apiUrl)
                .contentType("application/json");
    }
}
