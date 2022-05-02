package com.zakharenko.lab03.service.impl;

import com.zakharenko.lab03.dao.exception.DaoException;
import com.zakharenko.lab03.dao.impl.user.UserDao;
import com.zakharenko.lab03.entity.User;
import com.zakharenko.lab03.service.UserService;
import com.zakharenko.lab03.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
//    @Transactional
    public User signUp(User user, String repeatPassword) throws ServiceException {
        if(!user.getPassword().equals(repeatPassword)){
            throw new ServiceException("Passwords do not match");
        }
        if(!isUniqueLogin(user.getLogin())){
            throw new ServiceException("Login is not unique");
        }
//        User user = new User(login, password);
        try {
            userDao.create(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    private boolean isUniqueLogin(String login) throws ServiceException {
        try {
            return userDao.findByLogin(login).isEmpty();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
//    @Transactional
    public User signIn(String login, String password) throws ServiceException {
        List<User> result;
        try {
            result = userDao.findByLoginPassword(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        if(result.size() != 1){
            throw new ServiceException("Incorrect login or password");
        }
        return result.get(0);
    }

    @Override
//    @Transactional
    public List<User> showUsers() throws ServiceException {
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findUser(long id) throws ServiceException {
        try {
            return userDao.findOne(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
