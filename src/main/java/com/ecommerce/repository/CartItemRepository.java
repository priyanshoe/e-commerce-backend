package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.User;

public interface CartItemRepository extends JpaRepository<CartItem, Long > {

	List<CartItem> findAllByCustomer(User customer);

}
