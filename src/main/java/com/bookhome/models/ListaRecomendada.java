package com.bookhome.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class ListaRecomendada {

    private Long id;
    private String nombre;
    private String descripcion;
    private List<Libro> libros;
}
