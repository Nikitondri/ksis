package com.zakharenko.cloud_storage.cloud_storage.service;


import com.zakharenko.cloud_storage.cloud_storage.entity.User;

import java.util.List;

public interface UserService {
//    User signUp(String login, String password, String repeatPassword) throws ServiceException;
    User signUp(User user, String repeatPassword);
    User signIn(String login, String password);
    User findUser(long id);
    List<User> showUsers();
}
