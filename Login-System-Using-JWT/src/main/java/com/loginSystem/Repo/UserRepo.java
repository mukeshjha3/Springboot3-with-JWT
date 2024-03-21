package com.loginSystem.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loginSystem.Model.User;


@Repository
public interface UserRepo extends JpaRepository<User, String> {

	Optional<User> findByEmail(String Email);
	
	
}
