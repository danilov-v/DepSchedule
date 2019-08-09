package com.varb.schedule.security.service;


import com.varb.schedule.security.model.User;

public interface UserService {

    User findUserByEmail(String email);

    User saveUser(User user);

}
