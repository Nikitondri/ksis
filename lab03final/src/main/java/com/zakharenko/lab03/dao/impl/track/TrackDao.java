package com.zakharenko.lab03.dao.impl.track;

import com.zakharenko.lab03.dao.Dao;
import com.zakharenko.lab03.dao.exception.DaoException;
import com.zakharenko.lab03.entity.Track;

import java.util.List;

public interface TrackDao extends Dao<Track> {
    @Override
    List<Track> findAll() throws DaoException;

    @Override
    List<Track> createQuery(String query) throws DaoException;

    @Override
    Track findOne(final long id) throws DaoException;

    @Override
    Track create(final Track entity) throws DaoException;

    @Override
    Track update(final Track entity) throws DaoException;

    @Override
    void delete(final Track entity) throws DaoException;

    @Override
    void deleteById(final long id) throws DaoException;
}
