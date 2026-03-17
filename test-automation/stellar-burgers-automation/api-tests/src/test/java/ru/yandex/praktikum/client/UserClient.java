package ru.yandex.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.model.User;
import ru.yandex.praktikum.model.UserCredentials;

import static io.restassured.RestAssured.given;

public class UserClient {

    private static final String REGISTER = "/api/auth/register"; // :contentReference[oaicite:3]{index=3}
    private static final String LOGIN = "/api/auth/login";       // :contentReference[oaicite:4]{index=4}
    private static final String USER = "/api/auth/user";         // :contentReference[oaicite:5]{index=5}

    @Step("Регистрация пользователя")
    public Response register(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(REGISTER);
    }

    @Step("Логин пользователя")
    public Response login(UserCredentials creds) {
        return given()
                .header("Content-type", "application/json")
                .body(creds)
                .when()
                .post(LOGIN);
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .when()
                .delete(USER);
    }
}
