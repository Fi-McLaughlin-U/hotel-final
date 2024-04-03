package com.otu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.otu.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	//boolean existsByIdAndName(Long id, String name);
	boolean existsById(Long id);
	
	
	@Query("select c from Customer c where upper(c.name) like concat('%', upper(:name), '%')")
	List<Customer> findByName(@Param("name") String name);
	
}
