package com.bookhome.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LibroController {

    @GetMapping("/mis-libros")
    public String mostrarMisLibros() {
        return "views/mis-libros";
    }

    @GetMapping("/libros/{id}")
    public String mostrarDetalleLibro(@PathVariable Long id) {
        return "views/libro-detalle";
    }
}
