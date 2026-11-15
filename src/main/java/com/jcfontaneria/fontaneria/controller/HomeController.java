package com.jcfontaneria.fontaneria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/index", "/home"})
    public String mostrarHome() {
        return "index"; // Buscará index.html en src/main/resources/templates/
    }
}