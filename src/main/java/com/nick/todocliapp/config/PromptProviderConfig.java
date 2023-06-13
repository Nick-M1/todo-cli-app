package com.nick.todocliapp.config;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
public class PromptProviderConfig implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        return new AttributedString(
                "User:> ",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE)
        );
    }
}