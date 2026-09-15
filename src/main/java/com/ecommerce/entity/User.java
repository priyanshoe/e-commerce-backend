package com.ecommerce.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ecommerce.Dto.UserRequestDto;
import com.ecommerce.Enum.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users_data")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String email;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	private Timestamp createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updateAt;
	
	
	// convert userRequestDTO -> user
	public User(UserRequestDto newUser) {
		this.name = newUser.getName();
		this.email = newUser.getEmail();
		this.password = newUser.getPassword();
		this.role = newUser.getRole();
	}

}
