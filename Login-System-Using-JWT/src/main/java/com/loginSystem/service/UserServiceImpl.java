package com.loginSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.loginSystem.Model.User;
import com.loginSystem.Repo.UserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepo userRepo;

	@Override
	public User saveUser(User user) {
		User savedUser = userRepo.save(user);
		return savedUser;
	}

	@Override
	public String deleteUser(String id) {
		userRepo.deleteById(id);
		return "User with ID " + id +" Successfully deleted";
	}

	@Override
	public User updateUser(User user) {
		User updatedUser = User.builder().fullName(user.getFullName()).password(user.getPassword()).build();
		return updatedUser;
	}

	@Override
	public List<User> getAllUser() {
		List<User> findAll = userRepo.findAll();
		return findAll;
	}

	@Override
	public User getSingleUser(String id) {
		Optional<User> user = userRepo.findById(id);
		return user.get();
	}

}
