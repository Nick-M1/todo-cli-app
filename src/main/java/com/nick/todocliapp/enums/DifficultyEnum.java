package com.nick.todocliapp.enums;

import java.util.Map;

public enum DifficultyEnum {
    EASY, MEDIUM, DIFFICULT;

    public String toLowerCase() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

    static public Map<String, String> generateOptions() {
        return Map.of(
                EASY.toLowerCase(), EASY.name(),
                MEDIUM.toLowerCase(), MEDIUM.name(),
                DIFFICULT.toLowerCase(), DIFFICULT.name()
        );
    }
}
