package com.bookhome.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecomendacionController {

    @GetMapping("/recomendaciones")
    public String mostrarRecomendaciones() {
        return "views/recomendaciones";
    }
}
