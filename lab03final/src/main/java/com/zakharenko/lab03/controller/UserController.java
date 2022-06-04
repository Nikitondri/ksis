package com.zakharenko.lab03.controller;

import com.zakharenko.lab03.entity.User;
import com.zakharenko.lab03.exception.AccessException;
import com.zakharenko.lab03.exception.LoginException;
import com.zakharenko.lab03.service.UserService;
import com.zakharenko.lab03.service.exception.ServiceException;
import com.zakharenko.lab03.tool.CheckTotalSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import java.util.List;

@Controller
@RequestMapping("/users")
@SessionAttributes("user")
public class UserController {

    private final UserService userService;
    private final ServletContext context;

    @Autowired
    public UserController(UserService userService, ServletContext context) {
        this.userService = userService;
        this.context = context;
    }

    @PostMapping
    public String addUser(Model model, @ModelAttribute("user") User user, @RequestParam("repeatPassword") String repeatPassword){
        try {
            userService.signUp(user, repeatPassword);
            model.addAttribute("user", user);
            return "playlist/playlists";
        } catch (ServiceException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", new User());
            return "login";
        }
    }

    @GetMapping
    public String getUser(
            RedirectAttributes redirectAttributes,
            Model model,
            @RequestParam("login") String login,
            @RequestParam("password") String password) throws LoginException {
//        try {
        User user = userService.signIn(login, password);
        redirectAttributes.addFlashAttribute("user", user);
        return "redirect:/playlists";
//        } catch (ServiceException e){
//            throw new LoginException();
////            model.addAttribute("error", e.getMessage());
////            model.addAttribute("user", new User());
////            return "login";
//        }
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@ModelAttribute("user") User user, @PathVariable long id) throws ServiceException {
        userService.deleteUser(id, context.getRealPath("resources/music") + "\\" + id);
        return "redirect:/users/all";
    }

    @GetMapping("/all")
    public String getUsers(@ModelAttribute("user") User user, Model model) throws AccessException {
        if(user.isAdmin()) {
            try {
                List<User> users = userService.showUsers();
                model.addAttribute("users", users);
                model.addAttribute("sizeMap", CheckTotalSize.findMapTotalSizeUser(users));
                return "admin_page";
            } catch (ServiceException e) {
                return null;
            }
        } else {
            throw  new AccessException();
        }
    }

    @GetMapping("/{id}")
    public String findUser(RedirectAttributes redirectAttributes, @PathVariable long id){
        try {
            redirectAttributes.addFlashAttribute("user", userService.findUser(id));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "redirect:/playlists";
    }

    @ModelAttribute("user")
    public User createUser(){
        return new User();
    }


}
