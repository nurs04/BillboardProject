package com.example.billboardproject.controller;


import com.example.billboardproject.model.Billboard;
import com.example.billboardproject.model.User;
import com.example.billboardproject.service.BillboardService;
import com.example.billboardproject.service.OrderService;
import com.example.billboardproject.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MainController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BillboardService billboardService;

    @Autowired
    private UserServiceImpl userService;

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/basket")
    public String basketPage(Model model) {
        User currentUser = userService.getUserData();
        model.addAttribute("orders", orderService.getAllOrdersByUserId(currentUser.getId()));
        return "basket";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/detailBillboard/{billboard_id}")
    public String detailBillboardPage(Model model,
                                          @PathVariable(name = "billboard_id") Long id) {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        String dateString = dateFormat.format(new Date());

        int currentMonth = Integer.parseInt(dateString);
        Billboard billboard = billboardService.getBillboardById(id);
        if (billboard != null) {
            model.addAttribute("billboard", billboard);
            model.addAttribute("currentMonth", currentMonth);
        }
        return "details";
    }

}
