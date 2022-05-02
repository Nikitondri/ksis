package com.zakharenko.lab03.dao.impl.track;

import com.zakharenko.lab03.dao.AbstractDao;
import com.zakharenko.lab03.entity.Playlist;
import com.zakharenko.lab03.entity.Track;
import org.springframework.stereotype.Repository;

@Repository
public class TrackDaoImpl extends AbstractDao<Track> implements TrackDao {
    public TrackDaoImpl() {
        setClazz(Track.class);
    }
}
