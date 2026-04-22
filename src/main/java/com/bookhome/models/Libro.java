package com.bookhome.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Libro {

    private Long id;
    private String titulo;
    private String autor;
    private String genero;
    private String estadoLectura;
    private Integer puntuacion;
    private boolean favorito;
    private String comentario;

}
