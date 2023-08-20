package com.fercevik.programservice.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/programs")
public class ProgramController {

    @GetMapping
    public String echo() {
        String name = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Hello "+name;
    }
}
