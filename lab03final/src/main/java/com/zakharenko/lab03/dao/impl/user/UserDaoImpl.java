package com.zakharenko.lab03.dao.impl.user;

import com.zakharenko.lab03.dao.AbstractDao;
import com.zakharenko.lab03.dao.exception.DaoException;
import com.zakharenko.lab03.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao{
    public UserDaoImpl() {
        setClazz(User.class);
    }

    @Override
    public List<User> findByLogin(String login) throws DaoException {
        return createQuery("FROM User WHERE login='" + login + "'");
    }

    @Override
    public List<User> findByLoginPassword(String login, String password) throws DaoException {
        return createQuery("FROM User WHERE login='" + login + "' and password='" + password + "'");
    }
}
