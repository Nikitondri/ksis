package com.zakharenko.cloud_storage.cloud_storage.service.impl;

import com.zakharenko.cloud_storage.cloud_storage.entity.User;
import com.zakharenko.cloud_storage.cloud_storage.repository.UserRepository;
import com.zakharenko.cloud_storage.cloud_storage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
//    @Transactional
    public User signUp(User user, String repeatPassword) {
        if(!user.getPassword().equals(repeatPassword)){
//            throw new ServiceException("Passwords do not match");
            //TODO: add exception
            return null;
        }
        if(!isUniqueLogin(user.getLogin())){
//            throw new ServiceException("Login is not unique");
            return null;
            //TODO: add exception
        }
//        User user = new User(login, password);
//        try {
//            userDao.create(user);
            userRepository.save(user);
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
        return user;
    }

    private boolean isUniqueLogin(String login) {
//        try {
//            return userDao.findByLogin(login).isEmpty();
        return userRepository.findByLogin(login).isEmpty();
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
    }

    @Override
//    @Transactional
    public User signIn(String login, String password) {
//        List<User> result;
//        try {
//            result = userDao.findByLoginPassword(login, password);
        Optional<User> result = userRepository.findByLoginAndPassword(login, password);
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
        if(result.isEmpty()){
//            throw new ServiceException("Incorrect login or password");
            return null;
            //TODO: edd exception
        } else {
            return result.get();
        }
//        return result.get(0);
    }

    @Override
//    @Transactional
    public List<User> showUsers() {
//        try {
//            return userDao.findAll();
            return userRepository.findAll();
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
    }

    @Override
    public User findUser(long id) {
//        try {
//            return userDao.findOne(id);
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            return userOptional.get();
        } else {
            return null;
            //TODO: add exception
        }
    }
}
