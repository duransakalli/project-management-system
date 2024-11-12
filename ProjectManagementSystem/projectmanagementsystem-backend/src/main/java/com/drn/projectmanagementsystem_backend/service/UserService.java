package com.drn.projectmanagementsystem_backend.service;

import com.drn.projectmanagementsystem_backend.exception.UserNotFoundException;
import com.drn.projectmanagementsystem_backend.model.User;

public interface UserService {

    User findUserProfileByJwt(String jwt) throws UserNotFoundException;

    User findUserByEmail(String email) throws UserNotFoundException;

    User findUserById(Long userId) throws UserNotFoundException;

    User updateUsersProjectSize(User user, int number);
}
