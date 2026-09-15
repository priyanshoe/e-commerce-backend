package com.ecommerce.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User authUser = userRepo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(username+" not found"));
		return org.springframework.security.core.userdetails.User
				.withUsername(authUser.getEmail())
				.password(authUser.getPassword())
				.roles(authUser.getRole().name())
				.build();
	}

}
