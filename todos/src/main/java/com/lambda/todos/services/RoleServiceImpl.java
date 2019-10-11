package com.lambda.todos.services;

import com.lambda.todos.models.Role;
import com.lambda.todos.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepo;

	@Override
	public List<Role> findAll() {
		List<Role> list = new ArrayList<>();
		roleRepo.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public Role save(Role role) {
		Role newRole = new Role();
		newRole.setName(role.getName());
		if (role.getUserroles()
				.size() > 0)
		{
			throw new EntityNotFoundException("User Roles are not updated through Role. See endpoint POST: users/user/{userid}/role/{roleid}");
		}

		return roleRepo.save(role);
	}
}
