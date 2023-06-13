package com.nick.todocliapp.service;

import com.nick.todocliapp.enums.DifficultyEnum;
import com.nick.todocliapp.enums.PriorityEnum;
import com.nick.todocliapp.enums.ProgressEnum;
import com.nick.todocliapp.enums.PromptColor;
import com.nick.todocliapp.exception.BadRequestCommandException;
import com.nick.todocliapp.helper.ShellHelper;
import com.nick.todocliapp.model.Todo;
import com.nick.todocliapp.model.TodoFields;
import org.apache.logging.log4j.util.Strings;
import org.springframework.shell.component.flow.ComponentFlow;
import org.springframework.shell.component.flow.ResultMode;
import org.springframework.shell.component.flow.SelectItem;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComponentFlowBuilderService {
    private final ComponentFlow.Builder componentFlowBuilder;
    private final TodoService todoService;
    private final ShellHelper shellHelper;

    public ComponentFlowBuilderService(ComponentFlow.Builder componentFlowBuilder, TodoService todoService, ShellHelper shellHelper) {
        this.componentFlowBuilder = componentFlowBuilder;
        this.todoService = todoService;
        this.shellHelper = shellHelper;
    }

    public List<Todo> findByTagFlow(List<String> tags) {
        List<SelectItem> tagOptions = todoService.findAllTags()
                .stream().map(t -> SelectItem.of(t, t)).toList();

        List<String> tagsResultValues = tags == null ? new ArrayList<>() : tags;

        ComponentFlow flow = componentFlowBuilder.clone().reset()
                .withMultiItemSelector("tags")
                .name("Tags")
                .selectItems(tagOptions)
                .resultValues(tagsResultValues)
                .resultMode(ResultMode.ACCEPT)
                .and()
                .build();

        var result = flow.run().getContext();
        var tagsInput = (List<String>) result.get("tags");

        shellHelper.print("\nTodos found by tags provided:\n", PromptColor.GREEN);
        return todoService.findByTags(tagsInput.get(0));
    }

    public Todo findTodoByIdFlow(String id) {
        var idInput = getIdHelper(id, "search");

        shellHelper.print("\nTodos found by id=%s\n", PromptColor.GREEN, idInput);
        return todoService.findById(idInput);
    }

    public void deleteTodoFlow(String id) {
        var idInput = getIdHelper(id, "delete");
        todoService.delete(idInput);
        shellHelper.print("\nTodo deleted with id=%s\n", PromptColor.GREEN, idInput);
    }



    public Todo createTodoFlow(
            String title, String description, List<String> tags, String difficulty, String priority, String progress, String startAt, String endAt
    ) {
        var todoFields = getTodoFieldsHelper(
                title, description, tags, difficulty, priority, progress, startAt, endAt,
                "title...", "description...", "JAVA;PYTHON;JAVASCRIPT", LocalDate.now().toString(), LocalDate.now().toString(),
                "create"
        );

        shellHelper.print("\nNew todo created:\n", PromptColor.GREEN);
        return todoService.create(todoFields.title(), todoFields.description(), todoFields.tags(), todoFields.difficulty(), todoFields.priority(), todoFields.progress(), todoFields.startAt(), todoFields.endAt(), false);
    }

    public Todo updateTodoFlow(
            String id, String title, String description, List<String> tags, String difficulty, String priority, String progress, String startAt, String endAt
    ) {
        var idInput = getIdHelper(id, "update");
        var todo = todoService.findById(idInput);


        var todoFields = getTodoFieldsHelper(
                title, description, tags, difficulty, priority, progress, startAt, endAt,
                todo.getTitle(), todo.getDescription(), Strings.join(todo.getTags(), ';'), todo.getStartAt().toString(), todo.getEndAt().toString(),
                "update"
        );

        shellHelper.print("\nTodo with id=%s has been updated:\n", PromptColor.GREEN, idInput);
        return todoService.update(idInput, todoFields.title(), todoFields.description(), todoFields.tags(), todoFields.difficulty(), todoFields.priority(), todoFields.progress(), todoFields.startAt(), todoFields.endAt(), null);
    }

    public Todo markCompleteFlow(String id, Boolean uncompleted) {
        var idInput = getIdHelper(id, uncompleted ? "mark-incomplete" : "mark-complete");

        shellHelper.print("\nTodo with id=%s has been updated:\n", PromptColor.GREEN, idInput);
        return todoService.update(idInput, null, null, null, null, null, null, null, null, !uncompleted);
    }



    // HELPERS:
    private Long getIdHelper(String id, String confirmationTextCommand) {
        ComponentFlow flow = componentFlowBuilder.clone().reset()
                .withStringInput("id")
                .name("Todo ID")
                .defaultValue("1")
                .resultValue(id)
                .resultMode(ResultMode.ACCEPT)
                .and()

                .withConfirmationInput("confirmation")
                .name(String.format("Are you sure you want to %s this todo?", confirmationTextCommand))
                .resultMode(ResultMode.ACCEPT)
                .and()
                .build();


        var result = flow.run().getContext();

        var confirmationInput = result.get("confirmation", Boolean.class);
        if (!confirmationInput)
            throw new BadRequestCommandException(String.format("Todo %s canceled by user", confirmationTextCommand));

        return Long.valueOf(result.get("id", String.class));
    }

    private TodoFields getTodoFieldsHelper(
            String titleResult, String descriptionResult, List<String> tagsResult, String difficultyResult, String priorityResult, String progressResult, String startAtResult, String endAtResult,
            String titleDefault, String descriptionDefault, String tagsDefault, String startAtDefault, String endAtDefault,
            String confirmationTextCommand
    ) {
        var difficultySelectItems = DifficultyEnum.generateOptions();
        var prioritySelectItems = PriorityEnum.generateOptions();
        var progressSelectItems = ProgressEnum.generateOptions();

        ComponentFlow flow = componentFlowBuilder.clone().reset()
                .withStringInput("title")
                .name("Title")
                .defaultValue(titleDefault)
                .resultValue(titleResult)
                .resultMode(ResultMode.ACCEPT)
                .and()

                .withStringInput("description")
                .name("Description")
                .defaultValue(descriptionDefault)
                .resultValue(descriptionResult)
                .resultMode(ResultMode.ACCEPT)
                .and()

                .withStringInput("tags")
                .name("Tags - seperated by semicolons")
                .defaultValue(tagsDefault)
                .resultValue(Strings.join(tagsResult, ';'))
                .resultMode(ResultMode.ACCEPT)
                .and()

                .withSingleItemSelector("difficulty")
                .name("Difficulty")
                .selectItems(difficultySelectItems)
                .resultValue(difficultyResult)
                .resultMode(ResultMode.ACCEPT)
                .and()

                .withSingleItemSelector("priority")
                .name("Priority")
                .selectItems(prioritySelectItems)
                .resultValue(priorityResult)
                .resultMode(ResultMode.ACCEPT)
                .and()

                .withSingleItemSelector("progress")
                .name("Progress")
                .selectItems(progressSelectItems)
                .resultValue(progressResult)
                .resultMode(ResultMode.ACCEPT)
                .and()

                .withStringInput("startAt")
                .name("Start date (in format 'yyyy-mm-dd')")
                .defaultValue(startAtDefault)
                .resultValue(startAtResult)
                .resultMode(ResultMode.ACCEPT)
                .and()

                .withStringInput("endAt")
                .name("End date (in format 'yyyy-mm-dd')")
                .defaultValue(endAtDefault)
                .resultValue(endAtResult)
                .resultMode(ResultMode.ACCEPT)
                .and()

                .withConfirmationInput("confirmation")
                .name(String.format("Are you sure you want to %s this todo?", confirmationTextCommand))
                .resultMode(ResultMode.ACCEPT)
                .and()

                .build();


        var result = flow.run().getContext();

        var confirmationInput = result.get("confirmation", Boolean.class);
        if (!confirmationInput)
            throw new BadRequestCommandException(String.format("Todo %s canceled by user", confirmationTextCommand));

        var titleInput = result.get("title", String.class);
        var descriptionInput = result.get("description", String.class);
        var tagsInput = List.of(result.get("tags", String.class).split(";"));
        var difficultyInput = DifficultyEnum.valueOf(result.get("difficulty", String.class));
        var priorityInput = PriorityEnum.valueOf(result.get("priority", String.class));
        var progressInput = ProgressEnum.valueOf(result.get("progress", String.class));
        var startatInput = LocalDate.parse(result.get("startAt", String.class));
        var endatInput = LocalDate.parse(result.get("endAt", String.class));

        return new TodoFields(titleInput, descriptionInput, tagsInput, difficultyInput, priorityInput, progressInput, startatInput, endatInput);
    }

}
