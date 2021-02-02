package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUserService {

    @Autowired
    private UserMapper userMapper;
    private User loggedInUser = null;

    public void setUser(String username) {
        this.loggedInUser = userMapper.getUser(username);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public Integer getLoggedInUserId() throws NullPointerException{
        if(loggedInUser != null){
            return loggedInUser.getUserId();
        }else{
            throw new NullPointerException();
        }
    }

    public String getLoggedInUserName() throws NullPointerException {
        if (loggedInUser != null) {
            return loggedInUser.getUsername();
        } else {
            throw new NullPointerException();
        }
    }

    public String getLoggedInName () throws NullPointerException {
        if (loggedInUser != null) {
            return loggedInUser.getFirstname() + " " + loggedInUser.getLastname();
        } else {
            throw new NullPointerException();
        }
    }
}
