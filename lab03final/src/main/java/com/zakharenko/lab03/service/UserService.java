package com.zakharenko.lab03.service;

import com.zakharenko.lab03.entity.User;
import com.zakharenko.lab03.service.exception.ServiceException;

import java.util.List;

public interface UserService {
//    User signUp(String login, String password, String repeatPassword) throws ServiceException;
    User signUp(User user, String repeatPassword) throws ServiceException;
    User signIn(String login, String password) throws ServiceException;
    User findUser(long id) throws ServiceException;
    List<User> showUsers() throws ServiceException;
}
