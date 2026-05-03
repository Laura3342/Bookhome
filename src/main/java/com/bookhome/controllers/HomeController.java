package com.bookhome.controllers;

import com.bookhome.dto.GoogleBookItem;
import com.bookhome.dto.GoogleBooksResponse;
import com.bookhome.dto.VolumeInfo;
import com.bookhome.models.EstadoLectura;
import com.bookhome.models.Libro;
import com.bookhome.service.GoogleBooksService;
import com.bookhome.service.LibroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller

public class HomeController {
	
	
	@GetMapping("/")
	public String mostrarIndex() {
		return "views/index";
	 }
	
	@GetMapping("/home")
	public String mostrarHome() {
		return "views/home";
	}

	private final GoogleBooksService googleBooksService;
	private final LibroService libroService;

	public HomeController(GoogleBooksService googleBooksService,
	                      LibroService libroService) {
		this.googleBooksService = googleBooksService;
		this.libroService = libroService;
	}
	@GetMapping("/buscar-libros")
	public String buscarLibros(@RequestParam(required = false) String titulo,
	                           @RequestParam(required = false) String autor,
	                           @RequestParam(defaultValue = "0") int startIndex,
	                           Model model) {

		GoogleBooksResponse respuesta =
				googleBooksService.buscarLibros(titulo, autor, startIndex);

		model.addAttribute("libros", respuesta != null && respuesta.getItems() != null
				? respuesta.getItems()
				: List.of());

		model.addAttribute("titulo", titulo);
		model.addAttribute("autor", autor);
		model.addAttribute("startIndex", startIndex);

		return "views/resultados-busqueda";
	}

	@PostMapping("/buscar-libros/{idExterno}/agregar")
	public String agregarLibroDesdeGoogle(@PathVariable String idExterno,
	                                      RedirectAttributes redirectAttributes) {

		GoogleBookItem item = googleBooksService.buscarLibroPorId(idExterno);

		if (item == null || item.getVolumeInfo() == null) {
			redirectAttributes.addFlashAttribute("mensajeError", "No se pudo encontrar el libro.");
			return "redirect:/mis-libros";
		}

		VolumeInfo info = item.getVolumeInfo();

		String titulo = info.getTitle() != null ? info.getTitle() : "Sin título";

		String autor = info.getAuthors() != null && !info.getAuthors().isEmpty()
				? info.getAuthors().get(0)
				: "Autor desconocido";

		String genero = info.getCategories() != null && !info.getCategories().isEmpty()
				? info.getCategories().get(0)
				: "Sin categoría";

		String imagenUrl = info.getImageLinks() != null
				? info.getImageLinks().getThumbnail()
				: null;

		Libro libro = new Libro(
				System.currentTimeMillis(),
				item.getId(),
				titulo,
				autor,
				genero,
				imagenUrl,
				EstadoLectura.PENDIENTE,
				null,
				false,
				null
		);

		boolean agregado = libroService.agregarLibro(libro);

		if (agregado) {
			redirectAttributes.addFlashAttribute("mensajeExito", "Libro añadido desde Google Books.");
		} else {
			redirectAttributes.addFlashAttribute("mensajeError", "Este libro ya está en tu biblioteca.");
		}

		return "redirect:/mis-libros";
	}


}
