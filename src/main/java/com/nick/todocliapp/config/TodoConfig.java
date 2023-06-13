package com.nick.todocliapp.config;


import com.nick.todocliapp.enums.DifficultyEnum;
import com.nick.todocliapp.enums.PriorityEnum;
import com.nick.todocliapp.enums.ProgressEnum;
import com.nick.todocliapp.model.Todo;
import com.nick.todocliapp.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Order(-1)
@Slf4j
@Component
public class TodoConfig implements CommandLineRunner {

    private final TodoRepository todoRepository;

    public TodoConfig(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
            var todo1 = new Todo(
                "title1",
                "hello",
                List.of("hehe", "hehe2"),
                DifficultyEnum.DIFFICULT,
                PriorityEnum.LOW,
                ProgressEnum.IN_PROGRESS,
                LocalDate.now(),
                LocalDate.now(),
                false
        );

        todoRepository.saveAll(List.of(todo1))
                .forEach(todo -> log.info("Added todo to db: {}", todo.toString()));
    }
}