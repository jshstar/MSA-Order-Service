package com.sparta.msa_exam.auth.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService{

	@Value("${service.jwt.secret-key}")
	private String secretKey;

	@Value("${service.jwt.access-expiration}")
	private long expirationTime;

	@Override
	public String generateToken(String username) {
		return Jwts.builder()
			.setSubject(username)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
			.signWith(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey)))
			.compact();
	}
}
