package com.ecommerce.Dto;

import java.util.List;

import com.ecommerce.Enum.BusinessStatus;
import com.ecommerce.entity.Business;

import lombok.Data;

@Data
public class BusinessResponseDto {
	private Long id;
	private String name;
	private String description;
	private String email;
	private String phone;
	private String address;
	private BusinessStatus status;
	
	private List<ProductResponseDto> products;
	private OwnerInfo owner;
	
	public BusinessResponseDto(Business business) {
		this.id = business.getId();
		this.name = business.getName();
		this.description = business.getDescription();
		this.email = business.getEmail();
		this.phone = business.getPhone();
		this.address = business.getAddress();
		this.status = business.getStatus();
				
		OwnerInfo owner = new OwnerInfo();
		owner.setId(business.getSeller().getId());
		owner.setName(business.getSeller().getName());
		owner.setEmail(business.getSeller().getEmail());
		this.owner = owner;
		
		this.products = business.getProducts().stream()
				.map(ProductResponseDto::new).toList();
	}
	

}

@Data
class OwnerInfo{
	private Long id;
	private String name;
	private String email;
}