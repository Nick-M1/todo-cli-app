package com.nick.todocliapp.service;

import com.nick.todocliapp.enums.DifficultyEnum;
import com.nick.todocliapp.enums.PriorityEnum;
import com.nick.todocliapp.enums.ProgressEnum;
import com.nick.todocliapp.exception.ResourceNotFoundCommandException;
import com.nick.todocliapp.model.Todo;
import com.nick.todocliapp.repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }


    // TODOs
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Todo findById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundCommandException("Todo", "id", id));
    }

    public List<Todo> findByTags(String tag) {
        return todoRepository.findByTagsIsContainingIgnoreCase(tag);
    }

    public List<Todo> findByCompleteStatus(Boolean incomplete) {
        return todoRepository.findByIsComplete(!incomplete);
    }

    public Todo create(String title, String description, List<String> tags, DifficultyEnum difficulty, PriorityEnum priority, ProgressEnum progress, LocalDate startAt, LocalDate endAt, Boolean isComplete) {
        var todo = new Todo(title, description, tags, difficulty, priority, progress, startAt, endAt, isComplete);
        return todoRepository.save(todo);
    }

    @Transactional
    public Todo update(Long id, String title, String description, List<String> tags, DifficultyEnum difficulty, PriorityEnum priority, ProgressEnum progress, LocalDate startAt, LocalDate endAt, Boolean isComplete) {
        var todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundCommandException("Todo", "id", id));

        if (title != null && !title.equals(todo.getTitle()))
            todo.setTitle(title);
        if (description != null && !description.equals(todo.getDescription()))
            todo.setDescription(description);
        if (tags != null)
            todo.setTags(tags);
        if (difficulty != null && difficulty != todo.getDifficulty())
            todo.setDifficulty(difficulty);
        if (priority != null && priority != todo.getPriority())
            todo.setPriority(priority);
        if (progress != null && progress != todo.getProgress())
            todo.setProgress(progress);
        if (startAt != null && startAt != todo.getStartAt())
            todo.setStartAt(startAt);
        if (endAt != null && endAt != todo.getEndAt())
            todo.setEndAt(endAt);
        if (isComplete != null && isComplete != todo.getIsComplete())
            todo.setIsComplete(isComplete);

        return todo;
    }

    public void delete(Long id) {
        todoRepository.deleteById(id);
    }


    // TAGs
    public Set<String> findAllTags() {
        return todoRepository.findAllDistinctTags()
                .stream().flatMap(List::stream)
                .collect(Collectors.toSet());
    }
}
