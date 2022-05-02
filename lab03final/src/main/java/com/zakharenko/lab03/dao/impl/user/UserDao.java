package com.zakharenko.lab03.dao.impl.user;

import com.zakharenko.lab03.dao.Dao;
import com.zakharenko.lab03.dao.exception.DaoException;
import com.zakharenko.lab03.entity.User;

import java.util.List;

public interface UserDao extends Dao<User> {
    @Override
    List<User> findAll() throws DaoException;

    @Override
    List<User> createQuery(String query) throws DaoException;

    @Override
    User findOne(final long id) throws DaoException;

    @Override
    User create(final User entity) throws DaoException;

    @Override
    User update(final User entity) throws DaoException;

    @Override
    void delete(final User entity) throws DaoException;

    @Override
    void deleteById(final long id) throws DaoException;

    List<User> findByLogin(String login) throws DaoException;
    List<User> findByLoginPassword(String login, String password) throws DaoException;
}
