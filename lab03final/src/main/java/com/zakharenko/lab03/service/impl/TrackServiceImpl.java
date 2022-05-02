package com.zakharenko.lab03.service.impl;

import com.google.protobuf.ServiceException;
import com.zakharenko.lab03.dao.exception.DaoException;
import com.zakharenko.lab03.dao.impl.track.TrackDao;
import com.zakharenko.lab03.entity.Track;
import com.zakharenko.lab03.service.TrackService;
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
    public void uploadTrack(MultipartFile file, String path) throws ServiceException {
        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(
                            path
                    )
            );
            stream.write(bytes);
            stream.close();
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setPath(Track track, String path) throws ServiceException {
        track.setPath(path);
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
}
