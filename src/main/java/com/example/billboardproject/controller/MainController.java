package com.example.billboardproject.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = "/")
    public String authPage() {
        return "redirect:/auth/";
    }

    @GetMapping(value = "/forbidden")
    public String forbiddenPage() {
        return "forbiddenPage";
    }

    @PreAuthorize("isAuthenticated()" )
    @GetMapping(value = "/mainPage")
    public String profilePage() {
        return "mainPage";
    }

}
