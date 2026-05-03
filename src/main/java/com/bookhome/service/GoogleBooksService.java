package com.bookhome.service;

import com.bookhome.dto.GoogleBookItem;
import com.bookhome.dto.GoogleBooksResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoogleBooksService {
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${google.books.api.key}")
    private String apiKey;


    private int calcularPuntuacion(GoogleBookItem item, String busqueda) {

        int puntuacion = 0;

        if (item.getVolumeInfo() == null) return 0;

        var info = item.getVolumeInfo();

        // ✔ Tiene autores
        if (info.getAuthors() != null && !info.getAuthors().isEmpty()) {
            puntuacion += 2;
        }

        // ✔ Tiene portada
        if (info.getImageLinks() != null && info.getImageLinks().getThumbnail() != null) {
            puntuacion += 2;
        }

        // ✔ Tiene descripción
        if (info.getDescription() != null) {
            puntuacion += 1;
        }

        // ✔ Tiene categorías
        if (info.getCategories() != null && !info.getCategories().isEmpty()) {
            puntuacion += 1;
        }

        // ✔ Coincidencia con búsqueda
        if (info.getTitle() != null &&
                busqueda != null
                && !busqueda.isBlank()
                && info.getTitle().toLowerCase().contains(busqueda.toLowerCase())) {
            puntuacion += 3;
        }

        // ✔ Penalizar títulos largos (suelen ser ediciones raras)
        if (info.getTitle() != null && info.getTitle().length() > 60) {
            puntuacion -= 2;
        }

        return puntuacion;
    }

    public GoogleBooksResponse buscarLibros(String titulo,
                                            String autor,
                                            int startIndex) {

        StringBuilder consulta = new StringBuilder();

        if (titulo != null && !titulo.isBlank()) {
            consulta.append("intitle:").append(titulo.trim());
        }

        if (autor != null && !autor.isBlank()) {
            consulta.append(" inauthor:").append(autor.trim());
        }


        String url = UriComponentsBuilder
                .fromUriString("https://www.googleapis.com/books/v1/volumes")
                .queryParam("q", consulta.toString())
                .queryParam("maxResults", 40)
                .queryParam("startIndex", startIndex)
                .queryParam("orderBy", "relevance")
                .queryParam("printType", "books")
                .queryParam("key", apiKey)
                .toUriString();

        System.out.println("URL GOOGLE BOOKS: " + url);

        GoogleBooksResponse respuesta =
                restTemplate.getForObject(url, GoogleBooksResponse.class);

        if (respuesta == null || respuesta.getItems() == null) {
            return respuesta;
        }

        List<GoogleBookItem> items = new ArrayList<>(respuesta.getItems());

        items.sort((a, b) ->
                Integer.compare(
                        calcularPuntuacion(b, titulo),
                        calcularPuntuacion(a, titulo)
                )
        );

        if (items.size() > 12) {
            items = items.subList(0, 12);
        }

        respuesta.setItems(items);

        return respuesta;
    }

    public GoogleBookItem buscarLibroPorId(String idExterno) {
        String url = UriComponentsBuilder
                .fromUriString("https://www.googleapis.com/books/v1/volumes/" + idExterno)
                .queryParam("key", apiKey)
                .toUriString();

        return restTemplate.getForObject(url, GoogleBookItem.class);
    }
}
