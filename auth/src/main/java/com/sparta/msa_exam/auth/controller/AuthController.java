package com.sparta.msa_exam.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.msa_exam.auth.dto.UserRequest;
import com.sparta.msa_exam.auth.service.AuthServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class AuthController {

	private final AuthServiceImpl authService;

	@PostMapping("/auth/signIn")
	public ResponseEntity<?> signIn(@RequestBody UserRequest userRequest) {
		String accessToken = authService.signIn(userRequest);
		return ResponseEntity.ok()
			.header("Authorization", "Bearer " + accessToken)
			.body("Login successful");
	}

	@PostMapping("/auth/signUp")
	public ResponseEntity<?> signUp(@RequestBody UserRequest userRequest) {
		authService.signUp(userRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
	}




}
