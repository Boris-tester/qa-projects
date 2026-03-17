package ru.yandex.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class IngredientsClient {

    private static final String INGREDIENTS = "/api/ingredients"; // :contentReference[oaicite:6]{index=6}

    @Step("Получение списка ингредиентов")
    public Response getIngredients() {
        return given()
                .when()
                .get(INGREDIENTS);
    }
}
