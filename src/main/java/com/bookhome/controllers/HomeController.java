package com.bookhome.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

}
