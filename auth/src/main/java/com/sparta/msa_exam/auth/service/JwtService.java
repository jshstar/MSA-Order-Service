package com.sparta.msa_exam.auth.service;

import com.sparta.msa_exam.auth.entity.User;

public interface JwtService {

	String generateToken(User user);

	Long extractUserId(String token);
}
