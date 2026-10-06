package com.ecommerce.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
		.csrf(csrf -> csrf.disable())
//		.httpBasic(httpBasic -> {Customizer.withDefaults();})
		.cors(cors -> {})
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth -> auth
				// public api's
				.requestMatchers("/auth/**","/health","/status","/").permitAll()
				.requestMatchers(GET,"/product/**","/business/feature").permitAll()
				// admin api's
				.requestMatchers("/admin","/user").hasRole("ADMIN")
				// seller api's
				.requestMatchers("/business/**").hasRole("SELLER") // add order.PATCH
				.requestMatchers(GET,"/order/seller").hasRole("SELLER")
				.requestMatchers(PATCH,"/order/*").hasRole("SELLER")
				// customer api's
				.requestMatchers("/cart/**","/order/**").hasRole("CUSTOMER")
				
				.anyRequest().authenticated()
				)
		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() throws Exception {
		CorsConfiguration config = new CorsConfiguration();
		
		config.setAllowedOrigins(List.of("http://localhost:3000","http://localhost:5173",
				"https://e-commerce-five-mu-41.vercel.app", // production
				"https://e-commerce-git-working-priyanshoes-projects.vercel.app")); // pre-production
		config.setAllowedMethods(List.of("GET","POST","PATCH","PUT","DELETE","OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("**", config);
		
		return source;
	}
}







