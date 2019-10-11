package com.lambda.todos.controllers;

import com.lambda.todos.models.Todo;
import com.lambda.todos.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
public class TodoController {

	@Autowired
	private TodoService todoService;

	@PutMapping(value = "/todoid/{todoid}", consumes = {"application/json"})
	public ResponseEntity<?> completeTodo(@PathVariable long todoid, @RequestBody Todo todo){
		todoService.update(todo, todoid);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
