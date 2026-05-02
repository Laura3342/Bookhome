package com.bookhome.service;


import com.bookhome.models.Libro;
import com.bookhome.models.ListaRecomendada;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecomendacionService {


    private final List<ListaRecomendada> listasRecomendadas = new ArrayList<>();
    public RecomendacionService() {

        List<Libro> clasicos = new ArrayList<>();
        clasicos.add(new Libro(101L, "Orgullo y prejuicio", "Jane Austen", "Clásico", "Pendiente", null, false, ""));
        clasicos.add(new Libro(102L, "Don Quijote de la Mancha", "Miguel de Cervantes", "Clásico", "Pendiente", null, false, ""));
        clasicos.add(new Libro(103L, "Crimen y castigo", "Fiódor Dostoyevski", "Clásico", "Pendiente", null, false, ""));

        List<Libro> fantasia = new ArrayList<>();
        fantasia.add(new Libro(201L, "El hobbit", "J. R. R. Tolkien", "Fantasía", "Pendiente", null, false, ""));
        fantasia.add(new Libro(202L, "Harry Potter y la piedra filosofal", "J. K. Rowling", "Fantasía", "Pendiente", null, false, ""));
        fantasia.add(new Libro(203L, "El nombre del viento", "Patrick Rothfuss", "Fantasía", "Pendiente", null, false, ""));

        List<Libro> cienciaFiccion = new ArrayList<>();
        cienciaFiccion.add(new Libro(301L, "Dune", "Frank Herbert", "Ciencia ficción", "Pendiente", null, false, ""));
        cienciaFiccion.add(new Libro(302L, "Fahrenheit 451", "Ray Bradbury", "Ciencia ficción", "Pendiente", null, false, ""));
        cienciaFiccion.add(new Libro(303L, "Neuromante", "William Gibson", "Ciencia ficción", "Pendiente", null, false, ""));

        listasRecomendadas.add(new ListaRecomendada(
                1L,
                "Clásicos imprescindibles",
                "Una selección de obras clásicas recomendadas para cualquier lector.",
                clasicos
        ));

        listasRecomendadas.add(new ListaRecomendada(
                2L,
                "Fantasía y aventura",
                "Libros con mundos imaginarios, magia, viajes y grandes aventuras.",
                fantasia
        ));

        listasRecomendadas.add(new ListaRecomendada(
                3L,
                "Ciencia ficción",
                "Historias sobre tecnología, futuro, sociedad y mundos posibles.",
                cienciaFiccion
        ));
    }

    public List<ListaRecomendada> obtenerListas(){
        return listasRecomendadas;
    }

    public ListaRecomendada obtenerListaPorId(Long id) {
        for (ListaRecomendada lista : listasRecomendadas) {
            if (lista.getId().equals(id)) {
                return lista;
            }
        }
        return null;
    }

    public Libro obtenerLibroRecomendadoPorId(Long id) {
        for (ListaRecomendada lista : listasRecomendadas) {
            for (Libro libro : lista.getLibros()) {
                if (libro.getId().equals(id)) {
                    return libro;
                }
            }
        }
        return null;
    }
}
