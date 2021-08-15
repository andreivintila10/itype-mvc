package com.springmvc.service;

import java.util.List;

import com.springmvc.entity.User;

public interface UserService {

	public List<User> getUsers();

    public void saveUser(User user);

    public User getUser(long id);

    public User getUserByUsername(String username);

    public User validateUser(User user);

    public void updateUser(User user1, User user2);
    
    public void deleteUser(long id);
}
