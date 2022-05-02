package com.zakharenko.lab03.dao;

import com.zakharenko.lab03.dao.exception.DaoException;

import java.util.List;

public interface Dao<T> {
    List<T> findAll() throws DaoException;
    List<T> createQuery(String query) throws DaoException;
    T findOne(final long id) throws DaoException;
    T create(final T entity) throws DaoException;
    T update(final T entity) throws DaoException;
    void delete(final T entity) throws DaoException;
    void deleteById(final long id) throws DaoException;
}
