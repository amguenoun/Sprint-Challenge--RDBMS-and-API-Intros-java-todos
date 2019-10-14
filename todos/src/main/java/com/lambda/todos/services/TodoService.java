package com.lambda.todos.services;

import com.lambda.todos.models.Todo;

import java.util.List;

public interface TodoService {
	List<Todo> finalAll();

	Todo findTodoById(long id);

	Todo update(Todo todo, long id);
}
