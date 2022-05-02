package com.zakharenko.lab03.controller;

import com.zakharenko.lab03.entity.User;
import com.zakharenko.lab03.service.UserService;
import com.zakharenko.lab03.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
            @RequestParam("password") String password){
        try {
            User user = userService.signIn(login, password);
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/playlists";
        } catch (ServiceException e){
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", new User());
            return "login";
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


}
