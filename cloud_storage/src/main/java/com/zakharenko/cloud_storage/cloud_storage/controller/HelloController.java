package com.zakharenko.cloud_storage.cloud_storage.controller;

import com.zakharenko.cloud_storage.cloud_storage.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HelloController {

    @GetMapping
    public String goToLogin(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
}
