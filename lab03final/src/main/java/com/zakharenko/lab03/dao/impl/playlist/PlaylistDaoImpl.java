package com.zakharenko.lab03.dao.impl.playlist;

import com.zakharenko.lab03.dao.AbstractDao;
import com.zakharenko.lab03.entity.Playlist;
import com.zakharenko.lab03.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public class PlaylistDaoImpl extends AbstractDao<Playlist> implements PlaylistDao{
    public PlaylistDaoImpl() {
        setClazz(Playlist.class);
    }
}
