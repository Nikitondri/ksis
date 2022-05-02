package com.zakharenko.lab03.controller;

import com.zakharenko.lab03.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Neil Alishev
 */
@Controller
@RequestMapping("/")
public class HelloController {

    @GetMapping
    public String goToLogin(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
}
