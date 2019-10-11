package com.lambda.todos.controllers;

import com.lambda.todos.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
