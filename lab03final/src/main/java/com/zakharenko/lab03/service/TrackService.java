package com.zakharenko.lab03.service;

import com.google.protobuf.ServiceException;
import com.zakharenko.lab03.entity.Track;
import com.zakharenko.lab03.entity.User;
import com.zakharenko.lab03.exception.AccessException;
import org.springframework.web.multipart.MultipartFile;

public interface TrackService {
    int uploadTrack(MultipartFile file, String path) throws ServiceException;
    Track addTrack(Track track) throws ServiceException;
    void setPathAndSize(Track track, String path, int size) throws ServiceException;
    Track findTrackById(long id) throws ServiceException;
    void deleteTrack(Track track) throws ServiceException;
    Track updateTrack(Track track) throws ServiceException;
    boolean isHasUserTrack(User user, long trackId);
}
