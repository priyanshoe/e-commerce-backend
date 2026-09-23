package com.ecommerce.entity;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ecommerce.Dto.BusinessRequestDto;
import com.ecommerce.Enum.BusinessStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "businesses_data")
public class Business {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "seller_id", nullable = false)
	@JsonIgnore
	private User seller;
	
	private String name;
	private String description;
	private String email;
	private String phone;
	private String address;
	@Enumerated(EnumType.STRING)
	private BusinessStatus status = BusinessStatus.ACTIVE;
	
	@OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
	private List<Product> products;
	
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Timestamp createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private Timestamp updatedAt;
	
	public Business(BusinessRequestDto newBusiness) {
		this.name = newBusiness.getName();
		this.description = newBusiness.getDescription();
		this.email = newBusiness.getEmail();
		this.phone = newBusiness.getPhone();
		this.address = newBusiness.getAddress();
		this.status = BusinessStatus.ACTIVE;;
	}

}
