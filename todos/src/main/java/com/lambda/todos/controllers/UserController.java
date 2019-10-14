package com.lambda.todos.controllers;

import com.lambda.todos.models.Todo;
import com.lambda.todos.models.User;
import com.lambda.todos.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/mine", produces ={"application/json"})
	@ResponseBody
	public ResponseEntity<?> getCurrentUser(Authentication authentication){
		//if only returns username, then use services
		return new ResponseEntity<>(userService.findByName(authentication.getName()), HttpStatus.OK);
	}

	@PostMapping(value = "/user", consumes = {"application/json"})
	public ResponseEntity<?> createUser(@Valid @RequestBody User newUser){
		userService.save(newUser);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping(value = "/todo/{userid}", consumes = {"application/json"})
	public ResponseEntity<?> addTodoToUser(@Valid @RequestBody Todo newTodo, @PathVariable long userid){
		userService.addTodo(newTodo, userid);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/userid/{userid}")
	public ResponseEntity<?> deleteUser(@PathVariable long userid){
		userService.delete(userid);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
