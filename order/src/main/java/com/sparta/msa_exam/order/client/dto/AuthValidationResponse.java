package com.sparta.msa_exam.order.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthValidationResponse {
	private Long userId;
	private String userRole;
	private boolean hasUserRole;
}
