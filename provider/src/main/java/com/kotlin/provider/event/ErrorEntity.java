package com.kotlin.provider.event;

public class ErrorEntity {
    public static final int TYPE_LOGIN_SUCCESS = 200;
    public String error;
    public String token;
    public int code;

    public ErrorEntity(String error, int code) {
        this.error = error;
        this.code = code;
    }

}
