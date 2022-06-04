package com.zakharenko.lab03.controller;

import com.google.protobuf.ServiceException;
import com.zakharenko.lab03.entity.Playlist;
import com.zakharenko.lab03.entity.Track;
import com.zakharenko.lab03.entity.User;
import com.zakharenko.lab03.exception.AccessException;
import com.zakharenko.lab03.service.TrackService;
import com.zakharenko.lab03.tool.CheckTotalSize;
import com.zakharenko.lab03.tool.CryptoUtils;
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
import java.io.*;
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
        model.addAttribute("sizeMap", CheckTotalSize.findMapTotalSizeTrack(playlist.getTrackList()));
        return "track/new";
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> downloadTrack(@PathVariable long id, @SessionAttribute("user") User user) throws AccessException {
        try {
            Track track = trackService.findTrackById(id);
            if (track.getPlaylist().getUser().getId() == user.getId()) {
                byte[] bytes = CryptoUtils.decrypt(new FileInputStream(track.getPath()).readAllBytes());
                InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + track.getName())
                        .contentType(MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                        .contentLength(bytes.length) //
                        .body(resource);
            } else {
                throw new AccessException();
            }
        } catch (ServiceException | FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
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
            int size = trackService.uploadTrack(file, pathMusic);
            trackService.setPathAndSize(track, pathMusic, size);
        } catch (ServiceException e) {
            throw new ServiceException(e);
        }
        return "redirect:/tracks/new";
    }

    @DeleteMapping("/{id}")
    public String deleteTrack(@PathVariable long id, @SessionAttribute("playlist") Playlist playlist, @SessionAttribute("user") User user) throws AccessException {
        if(trackService.isHasUserTrack(user, id)) {
            try {
                trackService.deleteTrack(trackService.findTrackById(id));
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            return "redirect:/playlists/" + playlist.getId();
        } else {
            throw new AccessException();
        }
    }

    @GetMapping("update/{id}")
    public String goToUpdateTrack(@PathVariable long id, @SessionAttribute("user") User user, Model model){
        try {
            Track track = trackService.findTrackById(id);
            model.addAttribute("track", track);
            model.addAttribute("user", user);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "track/update";
    }

    @PatchMapping
    public String updateTrack(@ModelAttribute("track") Track track, @SessionAttribute("user") User user) throws AccessException {
        if(trackService.isHasUserTrack(user, track.getId())) {
            try {
                trackService.updateTrack(track);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            return "redirect:/playlists/" + track.getPlaylist().getId();
        } else {
            throw new AccessException();
        }
    }
}
