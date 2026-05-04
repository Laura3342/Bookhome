package com.bookhome.service;

import com.bookhome.dto.GoogleBookItem;
import com.bookhome.dto.GoogleBooksResponse;
import com.bookhome.dto.ImageLinks;
import com.bookhome.dto.VolumeInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoogleBooksService {
    private static final boolean USAR_DEMO = false;

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

        if (USAR_DEMO) {
            return generarResultadosDemo();
        }

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

        GoogleBooksResponse respuesta;

        try {
            respuesta = restTemplate.getForObject(url, GoogleBooksResponse.class);
        } catch (RestClientException e) {
            System.out.println("Google Books API no disponible: " + e.getMessage());

            GoogleBooksResponse respuestaVacia = new GoogleBooksResponse();
            respuestaVacia.setItems(List.of());
            return respuestaVacia;
        }

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

        if (USAR_DEMO) {
            return buscarLibroDemoPorId(idExterno);
        }


        String url = UriComponentsBuilder
                .fromUriString("https://www.googleapis.com/books/v1/volumes/" + idExterno)
                .queryParam("key", apiKey)
                .toUriString();

        try {
            return restTemplate.getForObject(url, GoogleBookItem.class);
        } catch (RestClientException e) {
            System.out.println("Error buscando libro por ID en Google Books: " + e.getMessage());
            return null;
        }
    }

    private GoogleBooksResponse generarResultadosDemo() {
        GoogleBooksResponse respuesta = new GoogleBooksResponse();

        List<GoogleBookItem> items = new ArrayList<>();

        items.add(crearLibroDemo(
                "demo-harry-potter",
                "Harry Potter y la piedra filosofal",
                "J.K. Rowling",
                "Fantasía",
                "/images/demo-harry-potter.jpg"
        ));

        items.add(crearLibroDemo(
                "demo-dune",
                "Dune",
                "Frank Herbert",
                "Ciencia ficción",
                "/images/demo-dune.jpg"
        ));

        items.add(crearLibroDemo(
                "demo-el-hobbit",
                "El hobbit",
                "J. R. R. Tolkien",
                "Fantasía",
                "/images/demo-hobbit.jpg"
        ));

        respuesta.setItems(items);
        return respuesta;
    }

    private GoogleBookItem crearLibroDemo(String id,
                                          String titulo,
                                          String autor,
                                          String categoria,
                                          String imagenUrl) {

        GoogleBookItem item = new GoogleBookItem();
        item.setId(id);

        VolumeInfo info = new VolumeInfo();
        info.setTitle(titulo);
        info.setAuthors(List.of(autor));
        info.setCategories(List.of(categoria));

        ImageLinks imageLinks = new ImageLinks();
        imageLinks.setThumbnail(imagenUrl);

        info.setImageLinks(imageLinks);
        item.setVolumeInfo(info);

        return item;
    }

    private GoogleBookItem buscarLibroDemoPorId(String idExterno) {
        return generarResultadosDemo().getItems().stream()
                .filter(item -> item.getId().equals(idExterno))
                .findFirst()
                .orElse(null);
    }



}
