package com.ecommerce.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ecommerce.Enum.Role;
import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;

@Configuration
public class DataSeeder {

	@Bean
	CommandLineRunner seedUser(
			UserRepository userRepo,
			PasswordEncoder passwordEncoder) {
		return args -> {
			if(userRepo.count() == 0) {
				User admin = new User();
				admin.setName("Admin generated");
				admin.setEmail("admin@shop.com");
				admin.setPassword(passwordEncoder.encode("123456"));
				admin.setRole(Role.ADMIN);
				userRepo.save(admin);

				User seller = new User();
				seller.setName("Seller generated");
				seller.setEmail("seller@shop.com");
				seller.setPassword(passwordEncoder.encode("123456"));
				seller.setRole(Role.SELLER);
				userRepo.save(seller);

				User customer = new User();
				customer.setName("Customer generated");
				customer.setEmail("customer@shop.com");
				customer.setPassword(passwordEncoder.encode("123456"));
				customer.setRole(Role.CUSTOMER);
				userRepo.save(customer);
				
				
				System.out.println("Default user created!");
			}
		};
	}
	
	
}
