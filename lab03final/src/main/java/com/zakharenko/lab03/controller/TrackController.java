package com.zakharenko.lab03.controller;

import com.google.protobuf.ServiceException;
import com.zakharenko.lab03.entity.Playlist;
import com.zakharenko.lab03.entity.Track;
import com.zakharenko.lab03.entity.User;
import com.zakharenko.lab03.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

@Controller
@RequestMapping("/tracks")
@SessionAttributes(value = "track")
public class TrackController {

    private final TrackService trackService;
    private final ServletContext context;

    @Autowired
    public TrackController(TrackService trackService, ServletContext context) {
        this.trackService = trackService;
        this.context = context;
    }

    @GetMapping("/new")
    public String goToNewTrack(
            Model model,
            @SessionAttribute("playlist") Playlist playlist,
            @SessionAttribute("user") User user
    ){
        model.addAttribute("user", user);
        model.addAttribute("playlist", playlist);
        return "track/new";
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> downloadTrack(@PathVariable long id){
        try {
            Track track = trackService.findTrackById(id);
            File file = new File(track.getPath());
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + track.getName())
                    .contentType(MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                    .contentLength(file.length()) //
                    .body(resource);
        } catch (ServiceException | FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping
    public String trackUpload(
            @SessionAttribute("playlist") Playlist playlist,
            @SessionAttribute("user") User user,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) throws ServiceException {
        try {
            Track track = new Track(
                    file.getResource().getFilename(),
                    0,
                    playlist,
                    null
            );
            track.setPlaylist(playlist);
            trackService.addTrack(track);
            playlist.addTrack(track);
            String pathDir = context.getRealPath("resources/music") + "\\" + user.getId() + "\\" + playlist.getId();
            if(!Files.isDirectory(Paths.get(pathDir), LinkOption.NOFOLLOW_LINKS)){
                try {
                    Files.createDirectories(Paths.get(pathDir));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String pathMusic = pathDir + "\\" + track.getId();
            trackService.uploadTrack(file, pathMusic);
            trackService.setPath(track, pathMusic);
        } catch (ServiceException e) {
            throw new ServiceException(e);
        }
        return "redirect:/tracks/new";
    }

    @DeleteMapping("/{id}")
    public String deleteTrack(@PathVariable long id, @SessionAttribute("playlist") Playlist playlist){
        try {
            trackService.deleteTrack(trackService.findTrackById(id));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "redirect:/playlists/" + playlist.getId();
    }

    @GetMapping("update/{id}")
    public String goToUpdateTrack(@PathVariable long id, Model model){
        try {
            Track track = trackService.findTrackById(id);
            model.addAttribute("track", track);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "track/update";
    }

    @PatchMapping
    public String updateTrack(@ModelAttribute("track") Track track){
        try {
            trackService.updateTrack(track);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "redirect:/playlists/" + track.getPlaylist().getId();
    }
}
