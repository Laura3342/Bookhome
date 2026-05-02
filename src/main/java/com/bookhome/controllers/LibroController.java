package com.bookhome.controllers;

import com.bookhome.models.EstadoLectura;
import com.bookhome.models.Libro;
import com.bookhome.service.LibroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }
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

    @PostMapping("/mis-libros/eliminar/{id}")
    public String eliminarLibro(@PathVariable Long id) {
        libroService.eliminarLibroPorId(id);
        return "redirect:/mis-libros";
    }

    @PostMapping("/mis-libros/estado/{id}")
    public String cambiarEstado(@PathVariable Long id,
                                @RequestParam String estadoLectura) {

        EstadoLectura estado = EstadoLectura.valueOf(estadoLectura);

        libroService.cambiarEstadoLectura(id, estado);

        return "redirect:/mis-libros";
    }

    @PostMapping("/mis-libros/favorito/{id}")
    public String cambiarFavorito(@PathVariable Long id) {
        libroService.cambiarFavorito(id);
        return "redirect:/mis-libros";
    }

    @PostMapping("/mis-libros/puntuacion/{id}")
    public String cambiarPuntuacion(@PathVariable Long id,
                                    @RequestParam Integer puntuacion) {
        libroService.cambiarPuntuacion(id, puntuacion);
        return "redirect:/mis-libros";
    }

    @PostMapping("/mis-libros/comentario/{id}")
    public String cambiarComentario(@PathVariable Long id,
                                    @RequestParam String comentario) {
        libroService.cambiarComentario(id, comentario);
        return "redirect:/mis-libros";
    }
}
