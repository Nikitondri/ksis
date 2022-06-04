package com.zakharenko.lab03.controller;

import com.zakharenko.lab03.entity.Playlist;
import com.zakharenko.lab03.entity.User;
import com.zakharenko.lab03.exception.AccessException;
import com.zakharenko.lab03.service.PlaylistService;
import com.zakharenko.lab03.service.UserService;
import com.zakharenko.lab03.service.exception.ServiceException;
import com.zakharenko.lab03.tool.CheckTotalSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;

@Controller
@RequestMapping("/playlists")
@SessionAttributes(value = {"playlist", "user"})
public class PlaylistController {

    private final PlaylistService playlistService;
    private final UserService userService;
    private final ServletContext context;
    private long userId;

    @Autowired
    public PlaylistController(PlaylistService playlistService, UserService userService, ServletContext context) {
        this.playlistService = playlistService;
        this.userService = userService;
        this.context = context;
    }

    @GetMapping
    public String showPlaylists(@ModelAttribute("user") User user, Model model){
        userId = user.getId();
        try {
            User newUser = userService.findUser(userId);
            model.addAttribute("user", newUser);
            model.addAttribute("sizeMap", CheckTotalSize.findMapTotalSizePlaylist(user.getPlaylistList()));
            model.addAttribute("totalSize", CheckTotalSize.findTotalSizeUser(newUser));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "/playlist/playlists";
    }

    @GetMapping("/new")
    public String goToNewPlaylist(
            @ModelAttribute("playlist") Playlist playlist,
            @ModelAttribute("user") User user,
            Model model){
        model.addAttribute("playlist", new Playlist());
        return "playlist/new";
    }

    @GetMapping("/{id}")
    public String findPlaylistById(Model model, @PathVariable long id, @ModelAttribute("playlist") Playlist playlist){
        try {
            playlist = playlistService.findPlaylist(id);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        model.addAttribute("playlist", playlist);
        model.addAttribute("sizeMap", CheckTotalSize.findMapTotalSizeTrack(playlist.getTrackList()));
        model.addAttribute("totalSize", CheckTotalSize.findTotalSizePlaylist(playlist));
        return "playlist/playlist";
    }

    @PostMapping
    public String addPlaylist(Model model, @ModelAttribute("user") User user, @ModelAttribute("playlist") Playlist playlist){
        playlist.setUser(user);
        user.addPlayList(playlist);
        try {
            playlistService.addPlaylist(playlist);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "/track/new";
    }

    @DeleteMapping("/{id}")
    public String deletePlaylist(@ModelAttribute("user") User user, @ModelAttribute("playlist") Playlist playlist, @PathVariable long id) throws AccessException {
        if(playlistService.isHasUserPlaylist(user, id)) {
            try {
                playlistService.deletePlaylist(
                        id,
                        context.getRealPath("resources/music") + "\\" + userId + "\\" + playlist.getId()
                );
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            return "redirect:/users/" + userId;
        } else {
            throw new AccessException();
        }
    }

    @GetMapping("update")
    public String goToUpdatePlaylist(){
        return "playlist/update";
    }

    @PatchMapping("/{id}")
    public String updatePlaylist(Model model, @ModelAttribute("playlist") Playlist playlist, @PathVariable long id, @ModelAttribute("user") User user) throws AccessException {
        if(playlistService.isHasUserPlaylist(user, id)) {
            try {
                model.addAttribute("playlist", playlistService.updatePlaylist(playlist));
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            return "redirect:/playlists/" + id;
        } else {
            throw new AccessException();
        }
    }

    @ModelAttribute("playlist")
    public Playlist createPlaylist(){
        return new Playlist();
    }

    @ModelAttribute("user")
    public User createUser(){
        return new User();
    }
}
