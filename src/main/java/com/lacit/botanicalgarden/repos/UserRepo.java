package com.lacit.botanicalgarden.repos;

import com.lacit.botanicalgarden.domain.User;

import java.util.List;

public interface UserRepo {

    List<User> findAll();
    User findOne(Long id);
    User findByUsername(String username);
    Long save(User user);
    int update(User user);
    int delete(Long id);
}
