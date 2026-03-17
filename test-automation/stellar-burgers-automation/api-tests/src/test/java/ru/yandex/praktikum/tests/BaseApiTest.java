package ru.yandex.praktikum.tests;

import org.junit.After;
import org.junit.BeforeClass;
import ru.yandex.praktikum.client.IngredientsClient;
import ru.yandex.praktikum.client.OrderClient;
import ru.yandex.praktikum.client.UserClient;
import ru.yandex.praktikum.config.RestAssuredConfig;

public class BaseApiTest {

    protected static final UserClient userClient = new UserClient();
    protected static final IngredientsClient ingredientsClient = new IngredientsClient();
    protected static final OrderClient orderClient = new OrderClient();

    protected String accessTokenToDelete; // если тест создал пользователя — удалим

    @BeforeClass
    public static void init() {
        RestAssuredConfig.setUp();
    }

    @After
    public void tearDown() {
        if (accessTokenToDelete != null) {
            userClient.deleteUser(accessTokenToDelete);
        }
    }
}
