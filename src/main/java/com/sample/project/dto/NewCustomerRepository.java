package com.sample.project.dto;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.project.entity.NewCustomer;

public interface NewCustomerRepository extends JpaRepository<NewCustomer, String> {

}
