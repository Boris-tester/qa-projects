package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.praktikum.model.Order;
import ru.yandex.praktikum.model.User;
import ru.yandex.praktikum.utils.UserGenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OrderCreateTest extends BaseApiTest {

    private List<String> getAnyIngredientIds() {
        return ingredientsClient.getIngredients()
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .extract()
                .path("data._id");
    }

    private String registerAndGetToken() {
        User user = UserGenerator.randomUser();
        String token = userClient.register(user)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .extract()
                .path("accessToken");

        accessTokenToDelete = token;
        return token;
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    @Description("Проверка: заказ создаётся при наличии токена авторизации, в ответе есть номер заказа.")
    public void shouldCreateOrderWithAuthTest() {
        String token = registerAndGetToken();

        List<String> ids = getAnyIngredientIds();
        Order order = new Order(Collections.singletonList(ids.get(0)));

        orderClient.createOrderWithAuth(order, token)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Проверка: заказ создаётся без токена авторизации, в ответе есть номер заказа.")
    public void shouldCreateOrderWithoutAuthTest() {
        List<String> ids = getAnyIngredientIds();
        Order order = new Order(Collections.singletonList(ids.get(0)));

        orderClient.createOrder(order)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами и авторизацией")
    @Description("Проверка: заказ создаётся с несколькими ингредиентами при наличии токена.")
    public void shouldCreateOrderWithIngredientsWithAuthTest() {
        String token = registerAndGetToken();

        List<String> ids = getAnyIngredientIds();
        List<String> twoIds = ids.size() >= 2
                ? Arrays.asList(ids.get(0), ids.get(1))
                : Collections.singletonList(ids.get(0));

        Order order = new Order(twoIds);

        orderClient.createOrderWithAuth(order, token)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Description("Проверка: при неверном id ингредиента сервер возвращает 500.")
    public void shouldReturnErrorForInvalidIngredientHashTest() {
        String token = registerAndGetToken();
        Order order = new Order(Collections.singletonList("invalid_hash"));

        orderClient.createOrderWithAuth(order, token)
                .then()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}



