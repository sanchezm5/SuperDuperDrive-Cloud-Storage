package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private UserMapper userMapper;
    private HashService hashService;
    private User currentUser = null;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public User getUser(String username) throws NullPointerException {
        return this.currentUser = userMapper.getUser(username);
    }

    public int createUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.insertUser(new User(user.getUsername(), encodedSalt, hashedPassword, user.getFirstname(), user.getLastname()));
    }

    public String getUsersName() {
        return this.currentUser.getFirstname() + " " + currentUser.getLastname();
    }

    public Integer getUserId() {
        return this.currentUser.getUserId();
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

}
