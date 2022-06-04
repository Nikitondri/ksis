package com.zakharenko.lab03.controller;

import com.zakharenko.lab03.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Neil Alishev
 */
@Controller
@RequestMapping("/")
@SessionAttributes("user")
public class HelloController {

    @GetMapping
    public String goToLogin(Model model, RedirectAttributes redirectAttributes) {
//        redirectAttributes.addFlashAttribute("user", new User());
        model.addAttribute("user", new User());
        return "login";
    }

    @ModelAttribute("user")
    public User createUser(){
        return new User();
    }
}
