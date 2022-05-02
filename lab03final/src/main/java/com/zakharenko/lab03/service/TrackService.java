package com.zakharenko.lab03.service;

import com.google.protobuf.ServiceException;
import com.zakharenko.lab03.entity.Track;
import org.springframework.web.multipart.MultipartFile;

public interface TrackService {
    void uploadTrack(MultipartFile file, String path) throws ServiceException;
    Track addTrack(Track track) throws ServiceException;
    void setPath(Track track, String path) throws ServiceException;
    Track findTrackById(long id) throws ServiceException;
    void deleteTrack(Track track) throws ServiceException;
    Track updateTrack(Track track) throws ServiceException;
}
