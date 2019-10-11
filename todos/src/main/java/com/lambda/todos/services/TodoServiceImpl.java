package com.lambda.todos.services;

import com.lambda.todos.models.Todo;
import com.lambda.todos.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "todoService")
public class TodoServiceImpl implements TodoService {
	@Autowired
	private TodoRepository todoRepo;

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
		Todo current = findTodoById(id);

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
}
