package com.bookhome.controllers;

import com.bookhome.models.Libro;
import com.bookhome.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping("/mis-libros")
    public String mostrarMisLibros(Model model) {
        model.addAttribute("libros", libroService.obtenerTodosLosLibros());
        return "views/mis-libros";
    }

    @GetMapping("/libros/{id}")
    public String mostrarDetalleLibro(@PathVariable Long id, Model model) {

        Libro libro = libroService.obtenerLibroPorId(id);
        model.addAttribute("libro", libro);
        return "views/libro-detalle";
    }
}
