package com.sample.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sample.project.entity.Customer;
import com.sample.project.service.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/customers")
	public String listCustomers(Model model) {
		model.addAttribute("customers", customerService.getAllCustomers());
		return "customers";
	}

	@GetMapping("/newCustomer")
	public String registerCustomer() {
		return "newCustomer";
	}

	@PostMapping("/newCustomer")
	public String registerCustomer(Customer customer) {
		customerService.registerCustomer(customer);
		return "redirect:/customers";
	}

	@GetMapping("/customers/{id}")
	public String updateCustomer(@PathVariable("id") Long id, Model model) {
		model.addAttribute("customer", customerService.getCustomerById(id));
		return "update";
	}

	@PostMapping("/customers/{id}")
	public String updateCustomer(@PathVariable("id") Long id, Customer customer) {
		customerService.updateCustomer(customer);
		return "redirect:/customers";
	}

}
