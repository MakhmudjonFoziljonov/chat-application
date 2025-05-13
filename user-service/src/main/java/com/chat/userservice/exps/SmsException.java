package com.chat.userservice.exps;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SmsException extends RuntimeException {
    public SmsException(String message) {
        super(message);
    }
}
