package com.nick.todocliapp.enums;

import java.util.Map;

public enum ProgressEnum {
    NOT_STARTED,
    IN_PROGRESS,
    NEEDS_REVIEW,
    COMPLETED;

    public String toLowerCase() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

    static public Map<String, String> generateOptions() {
        return Map.of(
                NOT_STARTED.toLowerCase(), NOT_STARTED.name(),
                IN_PROGRESS.toLowerCase(), IN_PROGRESS.name(),
                NEEDS_REVIEW.toLowerCase(), NEEDS_REVIEW.name(),
                COMPLETED.toLowerCase(), COMPLETED.name()
        );
    }
}
