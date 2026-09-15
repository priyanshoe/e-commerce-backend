package com.ecommerce.Dto;

import com.ecommerce.Enum.Role;
import com.ecommerce.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
	private String name;
	private String email;
	private String password;
	private Role role = Role.CUSTOMER;
	
	public UserRequestDto(User user) {
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.role = user.getRole();
	}
}
