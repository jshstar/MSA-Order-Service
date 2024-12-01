package com.sparta.msa_exam.auth.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sparta.msa_exam.auth.common.code.AuthErrorCode;
import com.sparta.msa_exam.auth.common.exception.AuthException;
import com.sparta.msa_exam.auth.entity.User;

import io.jsonwebtoken.Claims;
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
	public String generateToken(User user) {
		return Jwts.builder()
			.setSubject(user.getUsername().getValue())
			.setId(String.valueOf(user.getId()))
			.claim("role", String.valueOf(user.getUserRole()))
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
			.signWith(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey)))
			.compact();
	}

	@Override
	public Long extractUserId(String token) {
		try {

			if (token.startsWith("Bearer ")) {
				token = token.substring(7).trim();
			}
			SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
			Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
			String jtiStr = claims.getId();
			return Long.parseLong(jtiStr);
		} catch (Exception e) {
			log.error("Failed to extract user details from token: {}", e.getMessage());
			throw new IllegalArgumentException(new AuthException(AuthErrorCode.INVALID_TOKEN));
		}
	}
}
