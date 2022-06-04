package com.zakharenko.cloud_storage.cloud_storage.controller;

import com.zakharenko.cloud_storage.cloud_storage.entity.Package;
import com.zakharenko.cloud_storage.cloud_storage.entity.User;
import com.zakharenko.cloud_storage.cloud_storage.service.PackageService;
import com.zakharenko.cloud_storage.cloud_storage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.util.List;

@Controller
@RequestMapping("/packages")
@SessionAttributes(value = {"package", "user"})
public class PackageController {

    private final PackageService packageService;
    private final UserService userService;
    private final ServletContext context;
    private long userId;

    @Autowired
    public PackageController(PackageService packageService, UserService userService, ServletContext context) {
        this.packageService = packageService;
        this.userService = userService;
        this.context = context;
    }

    @GetMapping
    public String showPackage(@ModelAttribute("user") User user, Model model){
//            @ModelAttribute("playlist") Playlist playlist,
//            @ModelAttribute("user") User user,
//            Model model){
//        if(playlist == null){
//            playlist = new Playlist();
//        }
//        playlist.setName("playlist");
//        playlist.setUser(user);
//        model.addAttribute("user", user);

        userId = user.getId();
//        try {
            List<Package> playlists = packageService.findPackageByUserId(userId);
            model.addAttribute("user", userService.findUser(userId));
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
        return "/package/packages";
    }

    @GetMapping("/new")
    public String goToNewPackage(
            @ModelAttribute("package") Package aPackage,
            @ModelAttribute("user") User user,
            Model model){
        model.addAttribute("package", new Package());
        return "package/new";
    }

    @GetMapping("/{id}")
    public String findPackageById(Model model, @PathVariable long id, @ModelAttribute("package") Package aPackage){
//        try {
        aPackage = packageService.findPackage(id);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
        model.addAttribute("package", aPackage);
        return "package/packages";
    }

    @PostMapping
    public String addPackage(Model model, @ModelAttribute("user") User user, @ModelAttribute("package") Package aPackage){
//        Playlist playlist = new Playlist();
//        }
//        playlist.setName("playlist");
        aPackage.setUser(user);
        user.addPlayList(aPackage);
//        try {
        packageService.addPackage(aPackage);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
//        model.addAttribute("playlist", playlist);
        return "/file/new";
    }

    @DeleteMapping("/{id}")
    public String deletePackage(@ModelAttribute("user") User user, @ModelAttribute("package") Package aPackage, @PathVariable long id){
//        try {
            packageService.deletePackage(
                    id,
                    context.getRealPath("resources/files") + "\\" + userId + "\\" + aPackage.getId()
            );
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
        return "redirect:/users/" + userId;
    }

    @GetMapping("update")
    public String goToUpdatePackage(){
        return "package/update";
    }

    @PatchMapping("/{id}")
    public String updatePackage(Model model, @ModelAttribute("package") Package aPackage, @PathVariable long id){
//        try {
        model.addAttribute("playlist", packageService.updatePackage(aPackage));
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
        return "redirect:/packages/" + id;
    }

    @ModelAttribute("playlist")
    public Package createPlaylist(){
        return new Package();
    }

    @ModelAttribute("user")
    public User createUser(){
        return new User();
    }
}
