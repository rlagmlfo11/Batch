package com.sample.project.dto;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.project.entity.UpdatedPeople;

public interface UpdatedPeopleRepository extends JpaRepository<UpdatedPeople, Long> {

}
