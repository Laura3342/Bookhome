package com.bookhome.service;

import com.bookhome.models.EstadoLectura;
import com.bookhome.models.Libro;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.bookhome.models.EstadoLectura.*;


@Service
public class LibroService {
    private final List<Libro> libros = new ArrayList<>();

    public LibroService(){
        libros.add(new Libro(
                1L,
                "1",
                "Cien años de soledad",
                "Gabriel Garcia Márquez",
                "Realismo mágico",
                "",
                LEIDO,
                5,
                true,
                "Una obra impresionante."
        ));

        libros.add(new Libro(
                2L,
                "2",
                "1984",
                "George Orwell",
                "Distopía",
                "",
                LEYENDO,
                4,
                false,
                "Muy interesante y actual."
        ));

        libros.add(new Libro(
                3L,
                "3",
                "El principito",
                "Antoine de Saint-Exupéry",
                "Fábula",
                "",
                PENDIENTE,
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

    public boolean existeLibroPorTituloYAutor(String titulo, String autor) {
        return libros.stream()
                .anyMatch(libro ->
                        libro.getTitulo().equalsIgnoreCase(titulo)
                                && libro.getAutor().equalsIgnoreCase(autor)
                );
    }

    public boolean existeLibroPorIdExterno(String idExterno) {
        return libros.stream()
                .anyMatch(libro -> idExterno != null &&
                        idExterno.equals(libro.getIdExterno()));
    }

    public boolean agregarLibro(Libro libro) {
        if (libro == null) {
            return false;
        }

        if (existeLibroPorIdExterno(libro.getIdExterno())) {
            return false;
        }

        if (existeLibroPorTituloYAutor(libro.getTitulo(), libro.getAutor())) {
            return false;
        }

        libros.add(libro);
        return true;
    }

    public void eliminarLibroPorId(Long id) {
        libros.removeIf(libro -> libro.getId().equals(id));
    }
    public void cambiarEstadoLectura(Long id, EstadoLectura estadoLectura) {
        Libro libro = obtenerLibroPorId(id);

        if (libro != null) {
            libro.setEstadoLectura(estadoLectura);
        }
    }

    public void cambiarFavorito(Long id) {
        Libro libro = obtenerLibroPorId(id);

        if (libro != null) {
            libro.setFavorito(!libro.isFavorito());
        }
    }

    public void cambiarPuntuacion(Long id, Integer puntuacion) {
        Libro libro = obtenerLibroPorId(id);

        if (libro != null) {
            libro.setPuntuacion(puntuacion);
        }
    }

    public void cambiarComentario(Long id, String comentario) {
        Libro libro = obtenerLibroPorId(id);

        if (libro != null) {
            if (comentario == null || comentario.trim().isEmpty()) {
                libro.setComentario(null);
            } else {
                libro.setComentario(comentario);
            }
        }
    }

}