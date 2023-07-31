package com.practice.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogInController {

    @GetMapping("/log-in")
    public String logIn() {

        return "log-in";
    }
}
