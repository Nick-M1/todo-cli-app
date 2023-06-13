package com.nick.todocliapp.repository;

import com.nick.todocliapp.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT DISTINCT tag FROM Todo t JOIN t.tags tag")
    List<List<String>> findAllDistinctTags();

    List<Todo> findByTagsIsContainingIgnoreCase(String tags);

    List<Todo> findByIsComplete(Boolean isComplete);
}
