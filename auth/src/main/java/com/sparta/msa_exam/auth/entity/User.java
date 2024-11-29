package com.sparta.msa_exam.auth.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.sparta.msa_exam.auth.common.UserRole;
import com.sparta.msa_exam.auth.dto.UserRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "user_role", nullable = false)
	private UserRole userRole;

	public static User create(UserRequest request, PasswordEncoder passwordEncoder){
		return User.builder()
			.username(request.getUsername())
			.password(passwordEncoder.encode(request.getPassword()))
			.userRole(UserRole.USER)
			.build();
	}

}
