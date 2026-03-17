package ru.yandex.praktikum.utils;

import ru.yandex.praktikum.model.User;

public class UserGenerator {

    public static User randomUser() {
        long ts = System.currentTimeMillis();
        return new User(
                "user_" + ts + "@mail.ru",
                "password123",
                "Boris"
        );
    }
}
