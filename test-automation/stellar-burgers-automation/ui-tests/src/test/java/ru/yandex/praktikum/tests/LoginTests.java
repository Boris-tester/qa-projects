package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;
import ru.yandex.praktikum.api.UserApi;
import ru.yandex.praktikum.api.models.AuthResponse;
import ru.yandex.praktikum.base.BaseUiTest;
import ru.yandex.praktikum.pages.*;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class LoginTests extends BaseUiTest {

    private final UserApi userApi = new UserApi();

    private String email;
    private String password;
    private String accessToken;

    @Before
    public void createUserByApi() {
        email = generateEmail();
        password = "123456";

        Response registerResp = userApi.register(email, password, "Boris");
        registerResp.then().statusCode(SC_OK);

        AuthResponse body = registerResp.as(AuthResponse.class);
        accessToken = body.accessToken;
    }

    @After
    public void deleteUserByApi() {
        if (accessToken != null && !accessToken.isEmpty()) {
            Response deleteResp = userApi.deleteUser(accessToken);
            deleteResp.then().statusCode(anyOf(is(SC_OK), is(SC_ACCEPTED)));
        }
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной странице")
    @Description("Проверка входа: переход на форму логина с главной страницы, ввод валидных данных и успешная авторизация")
    public void shouldLoginFromMainLoginButtonTest() {
        new MainPage(driver)
                .open(baseUrl)
                .clickLoginToAccount();

        LoginPage login = new LoginPage(driver);
        login.setEmail(email)
                .setPassword(password);
        login.clickLogin();

        Assert.assertTrue("Пользователь должен быть залогинен", login.isLoggedIn());
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    @Description("Проверка входа: переход на форму логина через «Личный кабинет», ввод валидных данных и успешная авторизация")
    public void shouldLoginFromPersonalAccountButtonTest() {
        new MainPage(driver)
                .open(baseUrl)
                .clickPersonalAccount();

        LoginPage login = new LoginPage(driver);
        login.setEmail(email)
                .setPassword(password);
        login.clickLogin();

        Assert.assertTrue("Пользователь должен быть залогинен", login.isLoggedIn());
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    @Description("Проверка входа: переход на регистрацию, затем по ссылке на логин, ввод валидных данных и успешная авторизация")
    public void shouldLoginFromRegistrationFormLoginLinkTest() {
        new MainPage(driver)
                .open(baseUrl)
                .clickPersonalAccount();

        new LoginPage(driver)
                .clickRegisterLink();

        new RegisterPage(driver)
                .clickLoginLink();

        LoginPage login = new LoginPage(driver);
        login.setEmail(email)
                .setPassword(password);
        login.clickLogin();

        Assert.assertTrue("Пользователь должен быть залогинен", login.isLoggedIn());
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    @Description("Проверка входа: переход в восстановление пароля, затем по ссылке на логин, ввод валидных данных и успешная авторизация")
    public void shouldLoginFromForgotPasswordFormLoginLinkTest() {
        new MainPage(driver)
                .open(baseUrl)
                .clickPersonalAccount();

        new LoginPage(driver)
                .clickForgotPassword();

        new ForgotPasswordPage(driver)
                .clickLoginLink();

        LoginPage login = new LoginPage(driver);
        login.setEmail(email)
                .setPassword(password);
        login.clickLogin();

        Assert.assertTrue("Пользователь должен быть залогинен", login.isLoggedIn());
    }

    private String generateEmail() {
        return "test" + System.currentTimeMillis() + "@mail.ru";
    }
}
