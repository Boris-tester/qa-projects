package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.yandex.praktikum.model.User;
import ru.yandex.praktikum.utils.UserGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class UserRegisterTest extends BaseApiTest {

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Проверка: можно зарегистрировать нового пользователя, возвращается accessToken.")
    public void shouldCreateUniqueUserTest() {
        User user = UserGenerator.randomUser();

        // Сначала получаем токен и сохраняем, потом делаем проверки
        var response = userClient.register(user);
        String token = response.then().extract().path("accessToken");
        accessTokenToDelete = token;

        response.then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue());
    }

    @Test
    @DisplayName("Ошибка при создании уже зарегистрированного пользователя")
    @Description("Проверка: повторная регистрация с теми же данными возвращает 403 и 'User already exists'.")
    public void shouldNotCreateAlreadyRegisteredUserTest() {
        User user = UserGenerator.randomUser();

        var firstResponse = userClient.register(user);
        String token = firstResponse.then().extract().path("accessToken");
        accessTokenToDelete = token;

        firstResponse.then()
                .statusCode(SC_OK)
                .body("success", equalTo(true));

        userClient.register(user)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Ошибка при создании пользователя без email")
    @Description("Проверка: если не передать email, возвращается 403 и сообщение о required fields.")
    public void shouldNotCreateUserWithoutEmailTest() {
        User user = UserGenerator.randomUser();
        user.setEmail(null);

        userClient.register(user)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Ошибка при создании пользователя без password")
    @Description("Проверка: если не передать password, возвращается 403 и сообщение о required fields.")
    public void shouldNotCreateUserWithoutPasswordTest() {
        User user = UserGenerator.randomUser();
        user.setPassword(null);

        userClient.register(user)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Ошибка при создании пользователя без name")
    @Description("Проверка: если не передать name, возвращается 403 и сообщение о required fields.")
    public void shouldNotCreateUserWithoutNameTest() {
        User user = UserGenerator.randomUser();
        user.setName(null);

        userClient.register(user)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
