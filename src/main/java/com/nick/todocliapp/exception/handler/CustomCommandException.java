package com.nick.todocliapp.exception.handler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.ExitCodeGenerator;

@Getter
@Setter
abstract public class CustomCommandException extends RuntimeException implements ExitCodeGenerator {
    private final int exitCode;

    public CustomCommandException(String message, int exitCode) {
        super(message);
        this.exitCode = exitCode;
    }
}
