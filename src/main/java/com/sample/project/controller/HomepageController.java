package com.sample.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {

	@GetMapping("/home")
	public String home() {
		return "home"; // This refers to home.html in the templates directory
	}

}
