package com.sparta.msa_exam.auth.service;

import com.sparta.msa_exam.auth.dto.AuthValidationResponse;
import com.sparta.msa_exam.auth.dto.UserRequest;

public interface AuthService {


	String signIn(UserRequest userRequest);

	void signUp(UserRequest userRequest);

	AuthValidationResponse validateRole(String token);
}
