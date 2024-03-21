package com.loginSystem.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.loginSystem.Model.User;
import com.loginSystem.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {

	private final UserService service;
	private final PasswordEncoder passwordEncoder;

	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping("/register/admin")
	public User registerUser(@RequestBody User user) {
		System.out.println("hello this is mukesh");
		String userId = UUID.randomUUID().toString();
		user.setId(userId);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		/*
		 * Role role1 =
		 * Role.builder().id(UUID.randomUUID().toString()).roleName("ROLE_USER").user(
		 * user).build(); Role role2 =
		 * Role.builder().id(UUID.randomUUID().toString()).roleName("ROLE_ADMIN").user(
		 * user).build(); Set<Role> role = new HashSet<>(); role.add(role2);
		 * role.add(role1);
		 */
		user.setRole("ROLE_ADMIN");
		User usersaved = service.saveUser(user);
		return usersaved;
	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/hello")
	public String sayhello() {
		return "Hello";
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/goodmorning")
	public String saygoodMorning() {
		return "Good-Morning";
	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/adminlogin")
	public String login(HttpServletRequest request) {
		String username = request.getHeaders("username").toString();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication.getName());
		return "User LoggedIn Successfully";

	}
}
