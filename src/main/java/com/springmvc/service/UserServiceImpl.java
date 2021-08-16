package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springmvc.entity.User;
import com.springmvc.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public List<User> getUsers() {
		return userRepository.findAll();

	}

	@Override
	@Transactional
	public void saveUser(User user) {
		userRepository.save(user);

	}

	@Override
	@Transactional
	public User getUser(long id) {
		return userRepository.findById(id).orElse(null);

	}

	@Override
	@Transactional
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);

	}

	@Override
	@Transactional
	public User validateUser(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());

	}

	@Override
	public void updateUser(User user1, User user2) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public void deleteUser(long id) {
		userRepository.deleteById(id);

	}

}
