package com.sparta.msa_exam.auth.dto;

import com.sparta.msa_exam.auth.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthValidationResponse {
	private Long userId;
	private String userRole;
	private boolean hasUserRole;

	public AuthValidationResponse(User user){
		this.userId = user.getId();
		this.userRole = String.valueOf(user.getUserRole());
		this.hasUserRole = true;
	}
}
