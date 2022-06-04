package com.zakharenko.lab03.tool;

import com.zakharenko.lab03.entity.Playlist;
import com.zakharenko.lab03.entity.Track;
import com.zakharenko.lab03.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckTotalSize {

    public static Map<Long, Double> findMapTotalSizeUser(List<User> listUser){
        Map<Long, Double> result = new HashMap<>();
        for(User user : listUser){
            result.put(user.getId(), findTotalSizeUser(user));
        }
        return result;
    }

    public static Map<Long, Double> findMapTotalSizePlaylist(List<Playlist> listPlaylist){
        Map<Long, Double> result = new HashMap<>();
        for(Playlist playlist : listPlaylist){
            result.put(playlist.getId(), findTotalSizePlaylist(playlist));
        }
        return result;
    }

    public static Map<Long, Double> findMapTotalSizeTrack(List<Track> listTrack){
        Map<Long, Double> result = new HashMap<>();
        for(Track track : listTrack){
            result.put(track.getId(), findSizeTrack(track));
        }
        return result;
    }

    public static double findTotalSizeUser(User user){
        double totalSize = 0;
        for(Playlist playlist: user.getPlaylistList()){
            totalSize += findTotalSizePlaylist(playlist);
        }
        return totalSize;
    }

    public static double findTotalSizePlaylist(Playlist playlist){
        double totalSize = 0;
        for(Track track: playlist.getTrackList()){
            totalSize += findSizeTrack(track);
        }
        return totalSize;
    }

    //KB
    public static double findSizeTrack(Track track){
        return (double) track.getDuration() / 1000;
    }
}
