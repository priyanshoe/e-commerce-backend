package com.ecommerce.Dto;

import com.ecommerce.Enum.Role;
import com.ecommerce.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
	private Long id;
	private String name;
	private String email;
	private Role role;
	
	public UserResponseDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.role = user.getRole();
	}

}
