package com.sample.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sample.project.entity.People;
import com.sample.project.service.PeopleService;

@Controller
public class PeopleController {

	@Autowired
	private PeopleService peopleService;

	@GetMapping("/people")
	public String listPeople(Model model) {
		model.addAttribute("people", peopleService.getAllPeople());
		return "people";
	}

	@GetMapping("/newPeople")
	public String registerPeople() {
		return "newPeople";
	}

//	@PostMapping("/new")
//	public String registerPeople(People people) {
//		peopleService.registerPeople(people);
//		return "redirect:/people";
//	}
//
//	@GetMapping("/people/{id}")
//	public String updatePeople(@PathVariable("id") Long id, Model model) {
//		model.addAttribute("people", peopleService.getPeopleById(id));
//		return "update";
//	}
//
//	@PostMapping("/people/{id}")
//	public String updatePeople(@PathVariable("id") Long id, People people) {
//		peopleService.registerPeople(people);
//		return "redirect:/people";
//	}
}
