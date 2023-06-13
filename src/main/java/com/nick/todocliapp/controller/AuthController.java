package com.nick.todocliapp.controller;

import com.nick.todocliapp.enums.PromptColor;
import com.nick.todocliapp.helper.ShellHelper;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent(value = "Authentication")
public class AuthController {
    private final ShellHelper shellHelper;

    public AuthController(ShellHelper shellHelper) {
        this.shellHelper = shellHelper;
    }

    @ShellMethod(key = "greeting", value = "Greets user")
    public String greeting(
            @ShellOption(value = { "--name", "-n" }, defaultValue = "") String name
    ) {
        shellHelper.print("\nHELLO!!!!!\n", PromptColor.MAGENTA);

        return shellHelper.getColored(
                String.format("Welcome %s!", name),
                PromptColor.BLUE
        );
    }
}
