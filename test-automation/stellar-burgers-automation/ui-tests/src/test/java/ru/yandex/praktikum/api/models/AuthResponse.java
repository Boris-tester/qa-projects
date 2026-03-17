package ru.yandex.praktikum.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse {
    public boolean success;
    public String accessToken;
    public String refreshToken;
    public String message;
}
