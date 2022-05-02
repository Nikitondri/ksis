package com.zakharenko.lab03.dao.impl.playlist;

import com.zakharenko.lab03.dao.Dao;
import com.zakharenko.lab03.dao.exception.DaoException;
import com.zakharenko.lab03.entity.Playlist;

import java.util.List;

public interface PlaylistDao extends Dao<Playlist> {
    @Override
    List<Playlist> findAll() throws DaoException;

    @Override
    List<Playlist> createQuery(String query) throws DaoException;

    @Override
    Playlist findOne(final long id) throws DaoException;

    @Override
    Playlist create(final Playlist entity) throws DaoException;

    @Override
    Playlist update(final Playlist entity) throws DaoException;

    @Override
    void delete(final Playlist entity) throws DaoException;

    @Override
    void deleteById(final long id) throws DaoException;
}
