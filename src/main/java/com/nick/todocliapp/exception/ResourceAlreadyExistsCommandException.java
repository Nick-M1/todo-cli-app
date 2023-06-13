package com.nick.todocliapp.exception;

import com.nick.todocliapp.exception.handler.CustomCommandException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceAlreadyExistsCommandException extends CustomCommandException {
    private final static int EXIT_CODE = 1;

    public <T> ResourceAlreadyExistsCommandException(String itemName, String field, T id) {
        super(String.format("%s with %s=%s already exists\n\n", itemName, field, id), EXIT_CODE);
    }
}
