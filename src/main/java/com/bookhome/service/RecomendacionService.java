package com.bookhome.service;


import com.bookhome.models.Libro;
import com.bookhome.models.ListaRecomendada;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.bookhome.models.EstadoLectura.PENDIENTE;

@Service
public class RecomendacionService {


    private final List<ListaRecomendada> listasRecomendadas = new ArrayList<>();
    public RecomendacionService() {

        List<Libro> clasicos = new ArrayList<>();
        clasicos.add(new Libro(101L, "4","Orgullo y prejuicio", "Jane Austen", "Clásico", "", PENDIENTE, null, false, ""));
        clasicos.add(new Libro(102L, "5","Don Quijote de la Mancha", "Miguel de Cervantes", "Clásico", "", PENDIENTE, null, false, ""));
        clasicos.add(new Libro(103L,"6","Crimen y castigo", "Fiódor Dostoyevski", "Clásico", "",PENDIENTE, null, false, ""));

        List<Libro> fantasia = new ArrayList<>();
        fantasia.add(new Libro(201L,"7", "El hobbit", "J. R. R. Tolkien", "Fantasía","", PENDIENTE, null, false, ""));
        fantasia.add(new Libro(202L,"8", "Harry Potter y la piedra filosofal", "J. K. Rowling", "Fantasía", "", PENDIENTE, null, false, ""));
        fantasia.add(new Libro(203L, "9","El nombre del viento", "Patrick Rothfuss", "Fantasía", "", PENDIENTE, null, false, ""));

        List<Libro> cienciaFiccion = new ArrayList<>();
        cienciaFiccion.add(new Libro(301L, "10","Dune", "Frank Herbert", "Ciencia ficción",  "",PENDIENTE, null, false, ""));
        cienciaFiccion.add(new Libro(302L, "11","Fahrenheit 451", "Ray Bradbury", "Ciencia ficción","", PENDIENTE, null, false, ""));
        cienciaFiccion.add(new Libro(303L, "12","Neuromante", "William Gibson", "Ciencia ficción", "",PENDIENTE, null, false, ""));

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
