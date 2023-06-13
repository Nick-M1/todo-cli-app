package com.nick.todocliapp.exception.handler;

import org.springframework.shell.command.CommandExceptionResolver;
import org.springframework.shell.command.CommandHandlingResult;


public class CustomExceptionResolver implements CommandExceptionResolver {

    @Override
    public CommandHandlingResult resolve(Exception e) {
        if (e instanceof CustomCommandException customCommandException)
            return CommandHandlingResult.of(customCommandException.getMessage(), customCommandException.getExitCode());

        return null;
    }
}
