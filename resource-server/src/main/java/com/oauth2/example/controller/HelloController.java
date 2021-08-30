package com.oauth2.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/user/resource")
    public String hello() {
        return "8081user-resource";
    }

}