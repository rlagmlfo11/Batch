package com.sample.project.dto;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.project.entity.Update;

public interface UpdateRepository extends JpaRepository<Update, Long> {

}
