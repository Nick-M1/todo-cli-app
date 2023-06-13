package com.nick.todocliapp.model;

import com.nick.todocliapp.enums.DifficultyEnum;
import com.nick.todocliapp.enums.PriorityEnum;
import com.nick.todocliapp.enums.ProgressEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "todo_table")
public class Todo implements TableableModel {

    @Id @GeneratedValue
    Long id;
    String title;
    String description;

    @ElementCollection(fetch = FetchType.EAGER)
    List<String> tags;

    @Enumerated
    DifficultyEnum difficulty;
    @Enumerated
    PriorityEnum priority;
    @Enumerated
    ProgressEnum progress;

    LocalDate startAt;
    LocalDate endAt;

    Boolean isComplete;

    public Todo(String title, String description, List<String> tags, DifficultyEnum difficulty, PriorityEnum priority, ProgressEnum progress, LocalDate startAt, LocalDate endAt, Boolean isComplete) {
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.difficulty = difficulty;
        this.priority = priority;
        this.progress = progress;
        this.startAt = startAt;
        this.endAt = endAt;
        this.isComplete = isComplete;
    }

    public String[] asStringArray() {
        return new String[] {
                id.toString(),
                title,
                description,
                Strings.join(tags, ';'),
                difficulty.toString(),
                priority.toString(),
                progress.toString(),
                startAt.toString(),
                endAt.toString(),
                isComplete.toString(),
        };
    }

    public String[] tableHeader() {
        return new String[] {
                "ID",
                "Title",
                "Description",
                "Tags",
                "Difficulty",
                "Priority",
                "Progress",
                "Start date",
                "End date",
                "Is Complete"
        };
    }
}
