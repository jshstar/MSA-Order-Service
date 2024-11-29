package com.sparta.msa_exam.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.msa_exam.auth.common.code.AuthErrorCode;
import com.sparta.msa_exam.auth.common.exception.AuthException;
import com.sparta.msa_exam.auth.dto.UserRequest;
import com.sparta.msa_exam.auth.entity.User;
import com.sparta.msa_exam.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	private final JwtServiceImpl jwtService;


	@Override
	@Transactional(readOnly = true)
	public String signIn(UserRequest userRequest) {
		User user = userRepository.findByUsername(userRequest.getUsername())
			.orElseThrow(() -> new AuthException(AuthErrorCode.NOT_FOUND_USER));

		if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
			throw new AuthException(AuthErrorCode.INVALID_PASSWORD);
		}

		return jwtService.generateToken(user.getUsername());
	}


	@Override
	@Transactional
	public void signUp(UserRequest userRequest) {
		if (userRepository.existsByUsername(userRequest.getUsername())) {
			throw new AuthException(AuthErrorCode.DUPLICATE_USER);
		}

		User user = User.create(userRequest, passwordEncoder);
		userRepository.save(user);
	}
}
