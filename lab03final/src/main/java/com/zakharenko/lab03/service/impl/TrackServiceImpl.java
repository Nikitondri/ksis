package com.zakharenko.lab03.service.impl;

import com.google.protobuf.ServiceException;
import com.zakharenko.lab03.dao.exception.DaoException;
import com.zakharenko.lab03.dao.impl.track.TrackDao;
import com.zakharenko.lab03.entity.Playlist;
import com.zakharenko.lab03.entity.Track;
import com.zakharenko.lab03.entity.User;
import com.zakharenko.lab03.exception.AccessException;
import com.zakharenko.lab03.service.TrackService;
import com.zakharenko.lab03.tool.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class TrackServiceImpl implements TrackService {

    private final TrackDao trackDao;

    @Autowired
    public TrackServiceImpl(TrackDao trackDao) {
        this.trackDao = trackDao;
    }


    @Override
    public Track addTrack(Track track) throws ServiceException {
        try {
            return trackDao.create(track);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int uploadTrack(MultipartFile file, String path) throws ServiceException {
        try {
            byte[] bytes = file.getBytes();
            byte[] cryptoBytes = CryptoUtils.encrypt(bytes);
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(
                            path
                    )
            );
            stream.write(cryptoBytes);
            stream.close();
            return bytes.length;
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setPathAndSize(Track track, String path, int size) throws ServiceException {
        track.setPath(path);
        track.setDuration(size);
        try {
            trackDao.update(track);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Track findTrackById(long id) throws ServiceException {
        try {
            return trackDao.findOne(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteTrack(Track track) throws ServiceException {
        try {
            Files.delete(Paths.get(track.getPath()));
            trackDao.delete(track);
        } catch (DaoException | IOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Track updateTrack(Track track) throws ServiceException {
        try {
            return trackDao.update(track);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isHasUserTrack(User user, long trackId) {
        for(Playlist playlist : user.getPlaylistList()){
            for(Track track: playlist.getTrackList()){
                if(track.getId() == trackId){
                    return true;
                }
            }
        }
        return false;
    }
}
