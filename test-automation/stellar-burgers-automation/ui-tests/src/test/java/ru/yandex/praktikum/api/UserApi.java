package ru.yandex.praktikum.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.api.models.LoginRequest;
import ru.yandex.praktikum.api.models.RegisterRequest;

public class UserApi {

    private final ApiClient client = new ApiClient();

    @Step("API: регистрация пользователя {email}")
    public Response register(String email, String password, String name) {
        RegisterRequest body = new RegisterRequest(email, password, name);

        return client.spec()
                .body(body)
                .post(Endpoints.REGISTER);
    }

    @Step("API: логин пользователя {email}")
    public Response login(String email, String password) {
        LoginRequest body = new LoginRequest(email, password);

        return client.spec()
                .body(body)
                .post(Endpoints.LOGIN);
    }

    @Step("API: удалить пользователя")
    public Response deleteUser(String accessToken) {
        return client.spec()
                .header("Authorization", accessToken) // ожидается "Bearer ..."
                .delete(Endpoints.USER);
    }
}
