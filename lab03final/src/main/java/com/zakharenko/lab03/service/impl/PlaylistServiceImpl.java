package com.zakharenko.lab03.service.impl;

import com.zakharenko.lab03.dao.exception.DaoException;
import com.zakharenko.lab03.dao.impl.playlist.PlaylistDao;
import com.zakharenko.lab03.entity.Playlist;
import com.zakharenko.lab03.service.PlaylistService;
import com.zakharenko.lab03.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistDao playlistDao;

    @Autowired
    public PlaylistServiceImpl(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

    @Override
    public Playlist addPlaylist(Playlist playlist) throws ServiceException {
        try {
            return playlistDao.create(playlist);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Playlist findPlaylist(long id) throws ServiceException {
        try {
            Playlist playlist = playlistDao.findOne(id);
            return playlist;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deletePlaylist(long id, String pathFolder) throws ServiceException {
        try {
            deleteFolder(new File(pathFolder));
            playlistDao.deleteById(id);
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

    @Override
    public Playlist updatePlaylist(Playlist playlist) throws ServiceException {
        try {
            return playlistDao.update(playlist);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Playlist> findPlaylistByUserId(long userId) throws ServiceException {
        try {
            return playlistDao.createQuery("FROM Playlist WHERE user_id = " + userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
