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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("/recomendaciones/libro/{id}/agregar")
    public String agregarLibro(@PathVariable Long id,
                               RedirectAttributes redirectAttributes) {

        Libro libro = recomendacionService.obtenerLibroRecomendadoPorId(id);

        if (libro == null) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se ha encontrado el libro.");
            return "redirect:/recomendaciones";
        }

        boolean agregado = libroService.agregarLibro(libro);

        if (agregado) {
            redirectAttributes.addFlashAttribute("mensajeExito", "Libro añadido a tu biblioteca.");
        } else {
            redirectAttributes.addFlashAttribute("mensajeError", "Este libro ya está en tu biblioteca.");
        }

        return "redirect:/mis-libros";
    }


}
