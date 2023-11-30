package com.sample.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.project.dto.CustomerRepository;
import com.sample.project.entity.Customer;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public void registerCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	public void updateCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	public Customer getCustomerById(Long id) {
		Customer getCustomerById = customerRepository.findById(id).get();
		return getCustomerById;
	}

}
