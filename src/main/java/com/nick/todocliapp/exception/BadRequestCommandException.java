package com.nick.todocliapp.exception;

import com.nick.todocliapp.exception.handler.CustomCommandException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestCommandException extends CustomCommandException {
    private final static int EXIT_CODE = 2;

    public BadRequestCommandException(String message) {
        super(String.format("%s\n\n", message), EXIT_CODE);
    }
}
