package com.example.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping({"/login", "/login/"})
    public String login() { return "forward:/login/index.html"; }

    @GetMapping({"/signup", "/signup/"})
    public String signup() { return "forward:/signup/index.html"; }
}
