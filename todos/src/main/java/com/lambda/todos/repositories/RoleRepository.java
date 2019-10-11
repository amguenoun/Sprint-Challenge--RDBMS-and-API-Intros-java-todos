package com.lambda.todos.repositories;

import com.lambda.todos.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {
}
