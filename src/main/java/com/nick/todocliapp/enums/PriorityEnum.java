package com.nick.todocliapp.enums;

import java.util.Map;

public enum PriorityEnum {
    LOW, MEDIUM, HIGH;

    public String toLowerCase() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

    static public Map<String, String> generateOptions() {
        return Map.of(
                LOW.toLowerCase(), LOW.name(),
                MEDIUM.toLowerCase(), MEDIUM.name(),
                HIGH.toLowerCase(), HIGH.name()
        );
    }
}
