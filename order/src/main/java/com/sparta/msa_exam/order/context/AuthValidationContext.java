package com.sparta.msa_exam.order.context;

import com.sparta.msa_exam.order.client.dto.AuthValidationResponse;

public class AuthValidationContext {
	private static final ThreadLocal<AuthValidationResponse> CONTEXT = new ThreadLocal<>();

	public static void set(AuthValidationResponse authValidationResponse) {
		CONTEXT.set(authValidationResponse);
	}


	public static AuthValidationResponse get() {
		return CONTEXT.get();
	}

	public static Long getUserId() {
		AuthValidationResponse response = CONTEXT.get();
		return response != null ? response.getUserId() : null;
	}

	public static String getUserRole() {
		AuthValidationResponse response = CONTEXT.get();
		return response != null ? response.getUserRole() : null;
	}

	public static void clear() {
		CONTEXT.remove();
	}
}
