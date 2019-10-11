package com.lambda.todos.services;

import com.lambda.todos.models.Todo;
import com.lambda.todos.models.User;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserService
{
	UserDetails loadUserByUsername(String username);

	User save(User user);

	User findByName(String name);

	User addTodo(Todo todo, long id);

	void delete(long id);

}