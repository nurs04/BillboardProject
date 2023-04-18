package com.example.billboardproject.controller;


import com.example.billboardproject.service.BillboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private BillboardService billboardService;

    @GetMapping(value = "/")
    public String authPage() {
        return "redirect:/auth/";
    }

    @GetMapping(value = "/forbidden")
    public String forbiddenPage() {
        return "forbiddenPage";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/mainPage")
    public String profilePage(Model model) {
        model.addAttribute("billboards", billboardService.getAllActiveBillboards());
        return "mainPage";
    }

}
