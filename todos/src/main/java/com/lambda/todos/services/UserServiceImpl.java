package com.lambda.todos.services;

import com.lambda.todos.models.Role;
import com.lambda.todos.models.Todo;
import com.lambda.todos.models.User;
import com.lambda.todos.models.UserRoles;
import com.lambda.todos.repositories.RoleRepository;
import com.lambda.todos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username.toLowerCase());
		if (user == null)
		{
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(),
				user.getPassword(),
				user.getAuthority());
	}

	@Override
	public User save(User user) {
		if (userRepo.findByUsername(user.getUsername()) != null)
		{
			throw new EntityNotFoundException(user.getUsername() + " is already taken!");
		}

		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPasswordNoEncrypt(user.getPassword());
		newUser.setPrimaryemail(user.getPrimaryemail());

		ArrayList<UserRoles> newRoles = new ArrayList<>();
		for (UserRoles ur : user.getUserroles())
		{
			long id = ur.getRole()
					.getRoleid();
			Role role = roleRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException("Role id " + id + " not found!"));
			newRoles.add(new UserRoles(newUser,
					ur.getRole()));
		}
		newUser.setUserroles(newRoles);

		for(Todo t : user.getTodos()){
			newUser.getTodos().add(new Todo(t.getDescription(), t.getDatestarted(), newUser));
		}

		return userRepo.save(newUser);
	}

	@Override
	public User findByName(String name) {
		return userRepo.findByUsername(name);
	}

	@Override
	public void addTodo(Todo todo, long id) {
		User user =  userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User id " + id + " not found!"));
		todo.setUser(user);
		user.getTodos().add(todo);
	}
}


