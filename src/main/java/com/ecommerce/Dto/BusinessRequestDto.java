package com.ecommerce.Dto;

import com.ecommerce.Enum.BusinessStatus;
import com.ecommerce.entity.Business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessRequestDto {

	private String name;
	private String description;
	private String email;
	private String phone;
	private String address;
	private BusinessStatus status;
	
	public BusinessRequestDto(Business business) {
		this.name = business.getName();
		this.description = business.getDescription();
		this.email = business.getEmail();
		this.phone = business.getPhone();
		this.address = business.getAddress();
		this.status = business.getStatus();
	}
}
