package com.zakharenko.lab03.service;

import com.zakharenko.lab03.entity.Playlist;
import com.zakharenko.lab03.entity.User;
import com.zakharenko.lab03.exception.AccessException;
import com.zakharenko.lab03.service.exception.ServiceException;

import java.util.List;

public interface PlaylistService {
    Playlist addPlaylist(Playlist playlist) throws ServiceException;
    Playlist findPlaylist(long id) throws ServiceException;
    void deletePlaylist(long id, String pathFolder) throws ServiceException;
    Playlist updatePlaylist(Playlist playlist) throws ServiceException;
    List<Playlist> findPlaylistByUserId(long userId) throws ServiceException;
//    List<Playlist> findAllPlaylist(User user) throws ServiceException;
    boolean isHasUserPlaylist(User user, long playlistId);
}
