package com.ecommerce.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.Dto.ApiResponseDto;
import com.ecommerce.Dto.BusinessRequestDto;
import com.ecommerce.entity.Business;
import com.ecommerce.entity.User;
import com.ecommerce.exception.AccessDeniedException;
import com.ecommerce.exception.NotFound;
import com.ecommerce.repository.BusinessRepository;
import com.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BusinessService {

	private final BusinessRepository businessRepo;
	private final UserRepository userRepo;
	
	
	public ResponseEntity<ApiResponseDto> getBusinesses(){
		List<Business> businesses = businessRepo.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"businesses fetched",businesses));
	}
	
	
	public ResponseEntity<ApiResponseDto> getTop4Businesses(){
		List<Business> businesses = businessRepo.findTop4ByOrderByCreatedAt();
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"businesses fetched",businesses));
	}
	
	
	
	public ResponseEntity<ApiResponseDto> getBusinessById(Long id){
		Business business = businessRepo.findById(id).orElseThrow(()-> new NotFound("business"));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User user = userRepo.findByEmail(email).orElseThrow(()-> new NotFound("seller"));
		if(!business.getSeller().getId().equals(user.getId())) {
		    throw new AccessDeniedException("You don't own this business");
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"business fetched",business));
	}
	
	
	public ResponseEntity<ApiResponseDto> getMyBusinesses(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User seller = userRepo.findByEmail(email).orElseThrow(()-> new AccessDeniedException("not authenticated user"));
		List<Business> business = businessRepo.findBySeller(seller);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"seller businesses fetched",business));
	}
	
	
	public ResponseEntity<ApiResponseDto> addBusiness(BusinessRequestDto newBusiness){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User seller = userRepo.findByEmail(email).orElseThrow(()-> new NotFound("seller"));
		Business saveBusiness = new Business(newBusiness);
		saveBusiness.setSeller(seller);
		Business savedBusiness = businessRepo.save(saveBusiness);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto(true,"business added",savedBusiness));
	}
	
	
	public ResponseEntity<ApiResponseDto> UpdateBusiness(Long id ,BusinessRequestDto updateBusiness){
		Business existingBusiness = businessRepo.findById(id).orElseThrow(()-> new NotFound("business with id: "+id));
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User seller = userRepo.findByEmail(email).orElseThrow(()-> new NotFound("seller"));
		if (!existingBusiness.getSeller().getId().equals(seller.getId())) {
		    throw new AccessDeniedException("You don't own this business");
		}
		
		existingBusiness.setName(updateBusiness.getName());
		existingBusiness.setDescription(updateBusiness.getDescription());
		existingBusiness.setEmail(updateBusiness.getEmail());
		existingBusiness.setPhone(updateBusiness.getPhone());
		existingBusiness.setAddress(updateBusiness.getAddress());
		existingBusiness.setStatus(updateBusiness.getStatus());
		Business savedBusiness = businessRepo.save(existingBusiness);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"business updated",savedBusiness));
	}
	
	
	public ResponseEntity<ApiResponseDto> deleteBusiness(Long id){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String email = authentication.getName();
		User seller = userRepo.findByEmail(email).orElseThrow(()-> new NotFound("seller"));
		Business business = businessRepo.findById(id).orElseThrow(()-> new NotFound("business with id: "+id));
		if (!business.getSeller().getId().equals(seller.getId())) {
		    throw new AccessDeniedException("You don't own this business");
		}
		businessRepo.delete(business);;
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true,"business deleted",null));
	}
	
	
}
