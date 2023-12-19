package com.sample.project.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.project.entity.Kubun;

@Repository
public interface KubunRepository extends JpaRepository<Kubun, String> {

}
