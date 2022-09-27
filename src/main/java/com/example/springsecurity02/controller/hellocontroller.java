package com.example.springsecurity02.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class hellocontroller {

    @PostMapping("/login")
    public void hello( ){
        System.out.println("aaaaaaaaaaaaa");
    }
}
