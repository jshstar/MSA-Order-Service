package com.sparta.msa_exam.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.msa_exam.auth.common.code.AuthErrorCode;
import com.sparta.msa_exam.auth.common.exception.AuthException;
import com.sparta.msa_exam.auth.dto.AuthValidationResponse;
import com.sparta.msa_exam.auth.dto.UserRequest;
import com.sparta.msa_exam.auth.entity.User;
import com.sparta.msa_exam.auth.repository.UserRepository;
import com.sparta.msa_exam.auth.valueobject.Password;
import com.sparta.msa_exam.auth.valueobject.Username;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	private final JwtServiceImpl jwtService;


	@Override
	@Transactional(readOnly = true)
	public String signIn(UserRequest userRequest) {
		Username username = new Username(userRequest.getUsername());
		Password password = new Password(userRequest.getPassword());
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new AuthException(AuthErrorCode.NOT_FOUND_USER));

		if (!user.isPasswordMatch(password, passwordEncoder)) {
			throw new AuthException(AuthErrorCode.INVALID_PASSWORD);
		}

		return jwtService.generateToken(user);
	}


	@Override
	@Transactional
	public void signUp(UserRequest userRequest) {
		Username username = new Username(userRequest.getUsername());
		Password password = new Password(userRequest.getPassword());

		if (userRepository.existsByUsername(username)) {
			throw new AuthException(AuthErrorCode.DUPLICATE_USER);
		}

		User user = User.create(username, password, passwordEncoder);
		userRepository.save(user);
	}

	@Override
	@Transactional(readOnly = true)
	public AuthValidationResponse validateRole(String token){
		log.info(token);
		User user = userRepository.findById(jwtService.extractUserId(token))
			.orElseThrow(() -> new AuthException(AuthErrorCode.NOT_FOUND_USER));

		return new AuthValidationResponse(user);
	}
}
