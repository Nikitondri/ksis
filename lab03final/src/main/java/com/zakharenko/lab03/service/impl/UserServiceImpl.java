package com.zakharenko.lab03.service.impl;

import com.zakharenko.lab03.dao.exception.DaoException;
import com.zakharenko.lab03.dao.impl.role.RoleDao;
import com.zakharenko.lab03.dao.impl.user.UserDao;
import com.zakharenko.lab03.entity.Role;
import com.zakharenko.lab03.entity.User;
import com.zakharenko.lab03.exception.LoginException;
import com.zakharenko.lab03.service.UserService;
import com.zakharenko.lab03.service.exception.ServiceException;
import com.zakharenko.lab03.tool.EncryptPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public User signUp(User user, String repeatPassword) throws ServiceException {
        if(!user.getPassword().equals(repeatPassword)){
            throw new ServiceException("Passwords do not match");
        }
        if(!isUniqueLogin(user.getLogin())){
            throw new ServiceException("Login is not unique");
        }
        user.setPassword(EncryptPassword.encrypt(user.getPassword()));
        try {
            user.setRole(roleDao.findOne(1));
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
    public User signIn(String login, String plainPassword) throws LoginException {
        List<User> result;
        String password = EncryptPassword.encrypt(plainPassword);
        try {
            result = userDao.findByLoginPassword(login, password);
        } catch (DaoException e) {
            throw new LoginException();
        }
        if(result.size() != 1){
            throw new LoginException();
        }
        return result.get(0);
    }

    @Override
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

    @Override
    public void deleteUser(long id, String pathUser) throws ServiceException {
        try {
            deleteFolder(new File(pathUser));
            userDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private void deleteFolder(File file){
        if (!file.exists())
            return;

        //если это папка, то идем внутрь этой папки и вызываем рекурсивное удаление всего, что там есть
        if (file.isDirectory()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                // рекурсивный вызов
                deleteFolder(f);
            }
        }
        // вызываем метод delete() для удаления файлов и пустых(!) папок
        file.delete();
    }
}
