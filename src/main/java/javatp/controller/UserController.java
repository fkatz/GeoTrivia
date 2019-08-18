package javatp.controller;

import javatp.domain.User;
import javatp.exception.AuthenticationException;
import javatp.security.AuthenticationProvider;
import javatp.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value="/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationProvider auth;

	@PostMapping(value = "/auth")
	public ResponseEntity<Object> auth(@RequestBody User user) {
		try {
			String token = auth.generateToken(user);
			return ResponseEntity.ok(token);
		} catch (AuthenticationException aException) {
			throw aException;
		}
	}

	@PostMapping(value = "")
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		User newUser = userService.createUser(user);
		return ResponseEntity.ok(newUser);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getPOI(@PathVariable("id") long id) {
		User user = userService.getUser(id);
		return ResponseEntity.ok(user);
	}

	@GetMapping(value = "")
	public ResponseEntity<Object> getUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}
}