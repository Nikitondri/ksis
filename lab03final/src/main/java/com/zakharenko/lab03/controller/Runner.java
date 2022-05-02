package com.zakharenko.lab03.controller;

import com.zakharenko.lab03.dao.exception.DaoException;
import com.zakharenko.lab03.dao.impl.playlist.PlaylistDao;
import com.zakharenko.lab03.dao.impl.playlist.PlaylistDaoImpl;
import com.zakharenko.lab03.dao.impl.track.TrackDao;
import com.zakharenko.lab03.dao.impl.track.TrackDaoImpl;
import com.zakharenko.lab03.dao.impl.user.UserDao;
import com.zakharenko.lab03.dao.impl.user.UserDaoImpl;
import com.zakharenko.lab03.entity.Playlist;
import com.zakharenko.lab03.entity.Track;
import com.zakharenko.lab03.entity.User;
import com.zakharenko.lab03.service.TrackService;
import com.zakharenko.lab03.service.UserService;
import com.zakharenko.lab03.service.exception.ServiceException;
import com.zakharenko.lab03.service.impl.TrackServiceImpl;
import com.zakharenko.lab03.service.impl.UserServiceImpl;


public class Runner {
//    @Autowired
//    private UserService userService;


    public static void main(String[] args) throws ServiceException, com.google.protobuf.ServiceException {
//        TrackDao trackDao = new TrackDaoImpl();
//        try {
//            Track track = trackDao.findOne(1);
//            System.out.println(track);
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }


        PlaylistDao playlistDao = new PlaylistDaoImpl();
        try {
            Playlist playlist = playlistDao.findOne(1);
            System.out.println(playlist);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

}
