package com.bookhome.service;

import com.bookhome.models.Libro;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class LibroService {
    private final List<Libro> libros = new ArrayList<>();

    public LibroService(){
        libros.add(new Libro(
                1L,
                "Cien años de soledad",
                "Gabriel Garcia Márquez",
                "Realismo mágico",
                "Leído",
                5,
                true,
                "Una obra impresionante."
        ));

        libros.add(new Libro(
                2L,
                "1984",
                "George Orwell",
                "Distopía",
                "Leyendo",
                4,
                false,
                "Muy interesante y actual."
        ));

        libros.add(new Libro(
                3L,
                "El principito",
                "Antoine de Saint-Exupéry",
                "Fábula",
                "Pendiente",
                null,
                false,
                "Todavía no lo he empezado."
        ));

        }

    public List<Libro> obtenerTodosLosLibros(){
        return libros;
    }

    public Libro obtenerLibroPorId(Long id){
        for (Libro libro : libros){
            if(libro.getId().equals(id)){
                return libro;
            }
        }
        return null;
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }



}
