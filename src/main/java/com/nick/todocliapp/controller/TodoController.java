package com.nick.todocliapp.controller;

import com.nick.todocliapp.builder.TableBuilderCustom;
import com.nick.todocliapp.service.ComponentFlowBuilderService;
import com.nick.todocliapp.service.TodoService;
import jakarta.validation.constraints.Positive;
import org.springframework.shell.standard.AbstractShellComponent;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.Table;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Set;

@ShellComponent(value = "Todo-list")
@Validated
public class TodoController extends AbstractShellComponent {

    private final TodoService todoService;
    private final TableBuilderCustom tableBuilderCustom;
    private final ComponentFlowBuilderService componentFlowBuilderService;

    public TodoController(TodoService todoService, TableBuilderCustom tableBuilderCustom, ComponentFlowBuilderService componentFlowBuilderService) {
        this.todoService = todoService;
        this.tableBuilderCustom = tableBuilderCustom;
        this.componentFlowBuilderService = componentFlowBuilderService;
    }


    @ShellMethod(key = "todo find all", value = "Returns all todos")
    public Table findAll() {
        return tableBuilderCustom.buildTable(
                todoService.findAll());
    }

    @ShellMethod(key = "todo find by id", value = "Returns a todo, by id")
    public Table findById(
            @ShellOption(value = { "--id", "-i" }, defaultValue = ShellOption.NULL) @Positive String id
    ) {
        return tableBuilderCustom.buildTable(
                componentFlowBuilderService.findTodoByIdFlow(id));
    }

    @ShellMethod(key = "todo find by tag", value = "Returns a todo, by its tags")
    public Table findByTag(
            @ShellOption(value = { "--tag", "-t" }, defaultValue = ShellOption.NULL) List<String> tags
    ) {
        return tableBuilderCustom.buildTable(
                componentFlowBuilderService.findByTagFlow(tags));
    }

    @ShellMethod(key = "todo find by completion", value = "Returns all todos that are completed (if --incomplete flag, returns todos that are incomplete")
    public Table findTodosByCompleteStatus(
            @ShellOption(value = { "--incomplete", "-i" }, defaultValue = "false") Boolean incomplete
    ) {
        return tableBuilderCustom.buildTable(
                todoService.findByCompleteStatus(incomplete));
    }


    @ShellMethod(key = "todo create", value = "Creates a new todo")
    public Table createTodo(
            @ShellOption(value = { "--title", "-t" }, defaultValue = ShellOption.NULL)          String title,
            @ShellOption(value = { "--description", "-d" }, defaultValue = ShellOption.NULL)    String description,
            @ShellOption(value = { "--tags", "-a" }, defaultValue = ShellOption.NULL)           List<String> tags,
            @ShellOption(value = { "--difficulty", "-f" }, defaultValue = ShellOption.NULL)     String difficulty,
            @ShellOption(value = { "--priority", "-p" }, defaultValue = ShellOption.NULL)       String priority,
            @ShellOption(value = { "--progress", "-r" }, defaultValue = ShellOption.NULL)       String progress,
            @ShellOption(value = { "--startAt", "-s" }, defaultValue = ShellOption.NULL)        String startAt,
            @ShellOption(value = { "--endAt", "-e" }, defaultValue = ShellOption.NULL)          String endAt
    ) {

        return tableBuilderCustom.buildTable(
                componentFlowBuilderService.createTodoFlow(
                        title, description, tags, difficulty, priority, progress, startAt, endAt));
    }

    @ShellMethod(key = "todo update", value = "Updates a todo, by its id")
    public Table updateTodo(
            @ShellOption(value = { "--id", "-i" }, defaultValue = ShellOption.NULL)             @Positive String id,
            @ShellOption(value = { "--title", "-t" }, defaultValue = ShellOption.NULL)          String title,
            @ShellOption(value = { "--description", "-d" }, defaultValue = ShellOption.NULL)    String description,
            @ShellOption(value = { "--tags", "-a" }, defaultValue = ShellOption.NULL)           List<String> tags,
            @ShellOption(value = { "--difficulty", "-f" }, defaultValue = ShellOption.NULL)     String difficulty,
            @ShellOption(value = { "--priority", "-p" }, defaultValue = ShellOption.NULL)       String priority,
            @ShellOption(value = { "--progress", "-r" }, defaultValue = ShellOption.NULL)       String progress,
            @ShellOption(value = { "--startAt", "-s" }, defaultValue = ShellOption.NULL)        String startAt,
            @ShellOption(value = { "--endAt", "-e" }, defaultValue = ShellOption.NULL)          String endAt
    ) {
        return tableBuilderCustom.buildTable(
                componentFlowBuilderService.updateTodoFlow(id, title, description, tags, difficulty, priority, progress, startAt, endAt));
    }

    @ShellMethod(key = "todo completed", value = "Marks a todo as complete")
    public Table markTodoAsComplete(
            @ShellOption(value = { "--id", "-i" }, defaultValue = ShellOption.NULL) @Positive String id,
            @ShellOption(value = { "--uncompleted", "-u" }, defaultValue = "false") Boolean uncompleted
    ) {
        return tableBuilderCustom.buildTable(
                componentFlowBuilderService.markCompleteFlow(id, uncompleted));
    }


    @ShellMethod(key = "todo delete", value = "Deletes a todo, by id")
    public String deleteTodo(
            @ShellOption(value = { "--id", "-i" }, defaultValue = ShellOption.NULL) @Positive String id
    ) {
        var idInput = componentFlowBuilderService.deleteTodoFlow(id);
        return String.format("Deleted todo with id=%s", idInput);
    }


    // TAGs:
    @ShellMethod(key = "tags find", value = "Returns all distinct todo tags")
    Set<String> findAllTags() {
        return todoService.findAllTags();
    }
}
