package com.zakharenko.cloud_storage.cloud_storage.controller;

import com.zakharenko.cloud_storage.cloud_storage.entity.MyFile;
import com.zakharenko.cloud_storage.cloud_storage.entity.Package;
import com.zakharenko.cloud_storage.cloud_storage.entity.User;
import com.zakharenko.cloud_storage.cloud_storage.service.FileService;
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
@RequestMapping("/files")
@SessionAttributes(value = "file")
public class FileController {

    private final FileService fileService;
    private final ServletContext context;

    @Autowired
    public FileController(FileService fileService, ServletContext context) {
        this.fileService = fileService;
        this.context = context;
    }

    @GetMapping("/new")
    public String goToNewFile(
            Model model,
            @SessionAttribute("aPackage") Package aPackage,
            @SessionAttribute("user") User user
    ){
        model.addAttribute("user", user);
        model.addAttribute("aPackage", aPackage);
        return "file/new";
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable long id){
        try {
            MyFile track = fileService.findFileById(id);
            File file = new File(track.getPath());
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + track.getName())
                    .contentType(MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                    .contentLength(file.length()) //
                    .body(resource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //TODO: add exception
            return null;
        }
    }

    @PostMapping
    public String fileUpload(
            @SessionAttribute("package") Package aPackage,
            @SessionAttribute("user") User user,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
//        try {
            MyFile newFile = new MyFile(
                    file.getResource().getFilename(),
                    0,
                    aPackage,
                    null
            );
            newFile.setPlaylist(aPackage);
            fileService.addFile(newFile);
            aPackage.addTrack(newFile);
            String pathDir = context.getRealPath("resources/music") + "\\" + user.getId() + "\\" + aPackage.getId();
            if(!Files.isDirectory(Paths.get(pathDir), LinkOption.NOFOLLOW_LINKS)){
                try {
                    Files.createDirectories(Paths.get(pathDir));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String pathMusic = pathDir + "\\" + newFile.getId();
            fileService.uploadFile(file, pathMusic);
            fileService.setPath(newFile, pathMusic);
//        } catch (ServiceException e) {
//            throw new ServiceException(e);
//        }
        return "redirect:/files/new";
    }

    @DeleteMapping("/{id}")
    public String deleteFile(@PathVariable long id, @SessionAttribute("package") Package playlist){
//        try {
            fileService.deleteFile(fileService.findFileById(id));
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
        return "redirect:/packages/" + playlist.getId();
    }

    @GetMapping("update/{id}")
    public String goToUpdateTrack(@PathVariable long id, Model model){
//        try {
            MyFile track = fileService.findFileById(id);
            model.addAttribute("track", track);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
        return "file/update";
    }

    @PatchMapping
    public String updateTrack(@ModelAttribute("track") MyFile track){
//        try {
            fileService.updateFile(track);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
        return "redirect:/packages/" + track.getPlaylist().getId();
    }
}
