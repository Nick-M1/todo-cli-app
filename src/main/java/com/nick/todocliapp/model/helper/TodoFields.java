package com.nick.todocliapp.model.helper;

import com.nick.todocliapp.enums.DifficultyEnum;
import com.nick.todocliapp.enums.PriorityEnum;
import com.nick.todocliapp.enums.ProgressEnum;

import java.time.LocalDate;
import java.util.List;

public record TodoFields(
        String title,
        String description,
        List<String> tags,
        DifficultyEnum difficulty,
        PriorityEnum priority,
        ProgressEnum progress,
        LocalDate startAt,
        LocalDate endAt
) {

}

