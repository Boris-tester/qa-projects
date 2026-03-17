package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.model.User;
import ru.yandex.praktikum.model.UserCredentials;
import ru.yandex.praktikum.utils.UserGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class UserLoginTest extends BaseApiTest {

    private User user;

    @Before
    public void setUp() {
        user = UserGenerator.randomUser();

        String token = userClient.register(user)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .extract()
                .path("accessToken");

        accessTokenToDelete = token;
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    @Description("Проверка: пользователь может авторизоваться с корректными email и password.")
    public void shouldLoginWithExistingUserTest() {
        UserCredentials creds = new UserCredentials(user.getEmail(), user.getPassword());

        userClient.login(creds)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }

    @Test
    @DisplayName("Ошибка логина с неверным email")
    @Description("Проверка: при неверном email и корректном password возвращается 401 и сообщение об ошибке.")
    public void shouldNotLoginWithWrongEmailTest() {
        UserCredentials creds = new UserCredentials("wrong@email.ru", user.getPassword());

        userClient.login(creds)
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Ошибка логина с неверным password")
    @Description("Проверка: при корректном email и неверном password возвращается 401 и сообщение об ошибке.")
    public void shouldNotLoginWithWrongPasswordTest() {
        UserCredentials creds = new UserCredentials(user.getEmail(), "wrongPassword");

        userClient.login(creds)
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
