package com.kotlin.provider.event;

public class MessageLoginEvent {
    public String userId;
    public String userSign;
    public MessageLoginEvent(String userId, String userSign) {
        this.userId = userId;
        this.userSign = userSign;
    }
}
