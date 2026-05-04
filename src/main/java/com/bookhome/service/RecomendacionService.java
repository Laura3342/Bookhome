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
        clasicos.add(new Libro(101L, "4","Orgullo y prejuicio", "Jane Austen", "Clásico", "/images/orgullo-prejuicio.jpg", PENDIENTE, null, false, ""));
        clasicos.add(new Libro(102L, "5","Don Quijote de la Mancha", "Miguel de Cervantes", "Clásico", "/images/don-quijote.jpg", PENDIENTE, null, false, ""));
        clasicos.add(new Libro(103L,"6","Crimen y castigo", "Fiódor Dostoyevski", "Clásico", "/images/crimen-castigo.jpg",PENDIENTE, null, false, ""));
        clasicos.add(new Libro(101L, null, "Cien años de soledad", "Gabriel García Márquez", "Clásico", "/images/cien-años.jpg", PENDIENTE, null, false, ""));
        clasicos.add(new Libro(102L, null, "Rebelión en la granja", "George Orwell", "Clásico", "/images/rebelion-granja.jpg", PENDIENTE, null, false, ""));
        clasicos.add(new Libro(103L, null, "Frankenstein", "Mary Shelley", "Clásico", "/images/frankenstein.jpg", PENDIENTE, null, false, ""));



        List<Libro> fantasia = new ArrayList<>();
        fantasia.add(new Libro(201L,"7", "El hobbit", "J. R. R. Tolkien", "Fantasía","/images/demo-hobbit.jpg", PENDIENTE, null, false, ""));
        fantasia.add(new Libro(202L,"8", "Harry Potter y la piedra filosofal", "J. K. Rowling", "Fantasía", "/images/demo-harry-potter.jpg", PENDIENTE, null, false, ""));
        fantasia.add(new Libro(203L, "9","El nombre del viento", "Patrick Rothfuss", "Fantasía", "/images/nombre-viento.jpg", PENDIENTE, null, false, ""));
        fantasia.add(new Libro(201L, null, "El camino de los reyes", "Brandon Sanderson", "Fantasía", "/images/El-Camino-de-los-Reyes.jpg",PENDIENTE, null, false, ""));
        fantasia.add(new Libro(202L, null, "Trono de cristal", "Sarah J. Maas", "Fantasía", "/images/trono-cristal.jpg", PENDIENTE, null, false, ""));
        fantasia.add(new Libro(203L, null, "Babel", "R. F. Kuang", "Fantasía", "/images/Babel.jpg", PENDIENTE, null, false, ""));

        List<Libro> terror = new ArrayList<>();
        terror.add(new Libro(301L, null, "It", "Stephen King", "Terror", "/images/it.jpg", PENDIENTE, null, false, ""));
        terror.add(new Libro(302L, null, "Drácula", "Bram Stoker", "Terror", "/images/dracula.jpg", PENDIENTE, null, false, ""));
        terror.add(new Libro(303L, null, "El resplandor", "Stephen King", "Terror", "/images/resplandor.jpg", PENDIENTE, null, false, ""));
        terror.add(new Libro(304L, null, "Carrie", "Stephen King", "Terror", "/images/carrie.jpg", PENDIENTE, null, false, ""));
        terror.add(new Libro(305L, null, "Misery", "Stephen King", "Terror", "/images/misery.jpg", PENDIENTE, null, false, ""));
        terror.add(new Libro(306L, null, "La llamada de Cthulhu", "H. P. Lovecraft", "Terror", "/images/cthulhu.jpg", PENDIENTE, null, false, ""));


        List<Libro> romance = new ArrayList<>();
        romance.add(new Libro(401L, "13", "Antes de diciembre", "Joana Marcús", "Romance" ,"/images/antes-diciembre.jpg",PENDIENTE, null, false, ""));
        romance.add(new Libro(402L, "14", "Bajo la misma estrella", "John Green", "Romance", "/images/bajo-misma-estrella.jpg", PENDIENTE, null, false, ""));
        romance.add(new Libro(403L, "15", "Orgullo y prejuicio", "Jane Austen", "Romance", "/images/orgullo-prejuicio.jpg",PENDIENTE, null, false, ""));
        romance.add(new Libro(406L, null, "Normal People", "Sally Rooney", "Romance", "/images/normal-people.jpg", PENDIENTE, null, false, ""));
        romance.add(new Libro(401L, null, "The Poppy War", "R. F. Kuang", "Romance / Fantasía", "/images/poppy-war.jpg", PENDIENTE, null, false, ""));
        romance.add(new Libro(402L, null, "El priorato del naranjo", "Samantha Shannon", "Romance / Fantasía", "/images/priorato-naranjo.jpg", PENDIENTE, null, false, ""));





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
                4L,
                "Romance y drama",
                "Historias emocionales centradas en relaciones, sentimientos y crecimiento personal.",
                romance
        ));

        listasRecomendadas.add(new ListaRecomendada(
                3L,
                "Terror",
                "Historias oscuras, inquietantes y llenas de tensión.",
                terror
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
