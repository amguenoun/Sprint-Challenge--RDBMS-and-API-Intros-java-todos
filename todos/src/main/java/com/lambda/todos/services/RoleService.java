package com.lambda.todos.services;

import com.lambda.todos.models.Role;

import java.util.List;

public interface RoleService {
	List<Role> findAll();

	Role save(Role role);
}
