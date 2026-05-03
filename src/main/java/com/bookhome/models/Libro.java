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
    private String idExterno;
    private String titulo;
    private String autor;
    private String genero;
    private String imagenUrl;
    private EstadoLectura estadoLectura;
    private Integer puntuacion;
    private boolean favorito;
    private String comentario;


    public Libro(long id, String cienAñosDeSoledad, String gabrielGarciaMárquez, String realismoMágico, String leído, int puntuacion, boolean favorito, String comentario) {
    }
}
