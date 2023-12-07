package com.sample.project.dto;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.sample.project.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

// 한달보다 오래된 데이터가 있으면 삭제시키는 쿼리
//	@Transactional
//	@Modifying
//	@Query("DELTE FROM Customer c where c.date < :cufoffDate")
//	void deleteOlderThan(LocalDate cutoffDate);

}
