package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.Business;

public interface BusinessRepository extends JpaRepository<Business, Long> {

	List<Business> findBySeller_Id(Long id);

	void deleteAllBySeller_Id(Long id);

}
