package com.zakharenko.lab03.dao.impl.role;

import com.zakharenko.lab03.dao.Dao;
import com.zakharenko.lab03.dao.exception.DaoException;
import com.zakharenko.lab03.entity.Role;

import java.util.List;

public interface RoleDao extends Dao<Role> {
    @Override
    default List<Role> findAll() throws DaoException {
        return null;
    }

    @Override
    default List<Role> createQuery(String query) throws DaoException {
        return null;
    }

    @Override
    default Role findOne(final long id) throws DaoException {
        return null;
    }

    @Override
    default Role create(final Role entity) throws DaoException {
        return null;
    }

    @Override
    default Role update(final Role entity) throws DaoException {
        return null;
    }

    @Override
    default void delete(final Role entity) throws DaoException {

    }

    @Override
    default void deleteById(final long id) throws DaoException {

    }
}
