package com.sample.project.dto;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.project.entity.People;

public interface PeopleRepository extends JpaRepository<People, Long>{

}
