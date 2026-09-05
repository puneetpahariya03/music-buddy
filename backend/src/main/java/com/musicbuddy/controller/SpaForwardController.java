package com.musicbuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaForwardController {

    // Forward non-API, client-side routes to Angular index.html
    @GetMapping(value = {
        "/",
        "/songs",
        "/artists",
        "/playlists",
        "/playlists/**",
        "/favorites",
        "/auth/**"
    })
    public String forward() {
        return "forward:/index.html";
    }
}
