package com.sparta.msa_exam.auth.service;

public interface JwtService {

	String generateToken(String username);
}
