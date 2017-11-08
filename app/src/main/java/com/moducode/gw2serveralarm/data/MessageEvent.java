package com.moducode.gw2serveralarm.data;

/**
 * Created by Jay on 2017-11-08.
 */

public class MessageEvent {

    private final String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
