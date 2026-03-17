package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import ru.yandex.praktikum.api.UserApi;
import ru.yandex.praktikum.api.models.AuthResponse;
import ru.yandex.praktikum.base.BaseUiTest;
import ru.yandex.praktikum.pages.LoginPage;
import ru.yandex.praktikum.pages.MainPage;
import ru.yandex.praktikum.pages.RegisterPage;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class RegistrationTests extends BaseUiTest {

    private final UserApi userApi = new UserApi();

    private String email;
    private String password;
    private String accessToken;

    @After
    public void cleanup() {
        if (accessToken != null && !accessToken.isEmpty()) {
            Response deleteResp = userApi.deleteUser(accessToken);
            deleteResp.then().statusCode(anyOf(is(SC_OK), is(SC_ACCEPTED)));
        }
    }

    @Test
    @DisplayName("Успешная регистрация пользователя")
    @Description("Проверка регистрации: заполнение формы валидными данными, регистрация проходит успешно и открывается страница входа")
    public void shouldRegisterSuccessfullyTest() {
        email = generateEmail();
        password = "123456";
        String name = "Boris";

        new MainPage(driver)
                .open(baseUrl)
                .clickPersonalAccount();

        new LoginPage(driver)
                .clickRegisterLink();

        RegisterPage register = new RegisterPage(driver);
        register.fillForm(name, email, password);
        register.clickRegister();

        Assert.assertTrue(
                "После успешной регистрации должна открыться страница входа",
                register.isLoginPageOpened()
        );

        // Логинимся по API, чтобы получить токен и удалить пользователя в cleanup()
        Response loginResp = userApi.login(email, password);
        loginResp.then().statusCode(SC_OK);

        AuthResponse auth = loginResp.as(AuthResponse.class);
        accessToken = auth.accessToken;
    }

    @Test
    @DisplayName("Ошибка при регистрации с паролем короче 6 символов")
    @Description("Проверка валидации пароля: при вводе пароля длиной меньше 6 символов отображается сообщение об ошибке")
    public void shouldShowErrorForShortPasswordTest() {
        String shortPasswordEmail = generateEmail();

        new MainPage(driver)
                .open(baseUrl)
                .clickPersonalAccount();

        new LoginPage(driver)
                .clickRegisterLink();

        RegisterPage register = new RegisterPage(driver);
        register.fillForm("Boris", shortPasswordEmail, "12345");
        register.clickRegister();

        Assert.assertTrue(
                "Должна отображаться ошибка 'Некорректный пароль'",
                register.isPasswordErrorVisible()
        );
    }

    private String generateEmail() {
        return "test" + System.currentTimeMillis() + "@mail.ru";
    }
}
