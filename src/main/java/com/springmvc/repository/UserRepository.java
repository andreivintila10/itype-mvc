package com.springmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springmvc.entity.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);

	public User findByUsernameAndPassword(String username, String password);
}
