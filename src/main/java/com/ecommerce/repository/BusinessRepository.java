package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.Business;
import com.ecommerce.entity.User;

public interface BusinessRepository extends JpaRepository<Business, Long> {

	List<Business> findBySeller(User seller);

	List<Business> findTop4ByOrderByCreatedAt();

}
