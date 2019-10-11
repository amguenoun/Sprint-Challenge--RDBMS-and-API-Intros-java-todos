package com.lambda.todos.services;

import com.lambda.todos.models.Todo;
import com.lambda.todos.models.User;
import com.lambda.todos.repositories.TodoRepository;
import com.lambda.todos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "todoService")
public class TodoServiceImpl implements TodoService {
	@Autowired
	private TodoRepository todoRepo;

	@Autowired
	private UserRepository userRepo;

	@Override
	public List<Todo> finalAll() {
		List<Todo> list = new ArrayList<>();
		todoRepo.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public Todo findTodoById(long id) {
		return todoRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Todo with id " + id + " not found."));
	}

	@Override
	public Todo update(Todo todo, long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User authenticatedUser = userRepo.findByUsername(authentication.getName());
		Todo current = findTodoById(id);
		if(current.getUser().getUserid() == authenticatedUser.getUserid()){

			if(todo.getDatestarted() != null){
				current.setDatestarted(todo.getDatestarted());
			}

			if(todo.getDescription() != null){
				current.setDescription(todo.getDescription());
			}

			if(todo.isCompleted()){
				current.setCompleted(todo.isCompleted());
			}

			return todoRepo.save(current);
		}
		else{
			throw new EntityNotFoundException(id + " Not current user");
		}
	}
}
