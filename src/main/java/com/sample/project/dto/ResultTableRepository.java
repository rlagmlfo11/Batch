package com.sample.project.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.project.entity.ResultTable;

@Repository
public interface ResultTableRepository extends JpaRepository<ResultTable, String> {

}
