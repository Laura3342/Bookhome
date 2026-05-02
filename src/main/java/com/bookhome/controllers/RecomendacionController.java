package com.bookhome.controllers;

import com.bookhome.models.Libro;
import com.bookhome.models.ListaRecomendada;
import com.bookhome.service.LibroService;
import com.bookhome.service.RecomendacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RecomendacionController {

    @Autowired
    private RecomendacionService recomendacionService;

    @Autowired
    private LibroService libroService;

    @GetMapping("/recomendaciones")
    public String mostrarRecomendaciones(Model model) {
        model.addAttribute("listas", recomendacionService.obtenerListas());
        return "views/recomendaciones";
    }

    @GetMapping("/recomendaciones/{id}")
    public String mostrarDetalleLista(@PathVariable Long id, Model model) {

        ListaRecomendada lista = recomendacionService.obtenerListaPorId(id);

        model.addAttribute("lista", lista);

        return "views/detalle-lista";
    }

    @GetMapping("/recomendaciones/libro/{id}/agregar")
    public String agregarLibroABiblioteca(@PathVariable Long id) {

        Libro libro = recomendacionService.obtenerLibroRecomendadoPorId(id);

        if (libro != null) {
            libroService.agregarLibro(libro);
        }

        return "redirect:/mis-libros";
    }



}
