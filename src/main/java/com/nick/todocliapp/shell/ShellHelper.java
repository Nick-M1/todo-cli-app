package com.nick.todocliapp.shell;

import com.nick.todocliapp.enums.PromptColor;
import lombok.NoArgsConstructor;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class ShellHelper {

    public String getColoredMessage(CharSequence message, PromptColor color) {
        return new AttributedStringBuilder()
                .append(message, AttributedStyle.DEFAULT.foreground(color.toJlineAttributedStyle())).toAnsi();
    }

    public void print(CharSequence message, PromptColor color, Object... args) {
        System.out.printf(getColoredMessage(message, color), args);
    }
}
