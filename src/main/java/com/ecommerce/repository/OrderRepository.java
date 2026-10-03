package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findAllByCustomer(User customer);

	@Query("""
	        SELECT DISTINCT o
	        FROM Order o
	        JOIN o.orderItems oi
	        JOIN oi.product p
	        WHERE p.business.id = :businessId
	    """)
	    List<Order> findOrdersByBusinessId(Long businessId);


}
