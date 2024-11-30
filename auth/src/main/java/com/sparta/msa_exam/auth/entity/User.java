package com.sparta.msa_exam.auth.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.sparta.msa_exam.auth.common.UserRole;
import com.sparta.msa_exam.auth.valueobject.Password;
import com.sparta.msa_exam.auth.valueobject.Username;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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

	@Embedded
	private Username username;

	@Embedded
	private Password password;


	@Enumerated(EnumType.STRING)
	@Column(name = "user_role", nullable = false)
	private UserRole userRole;

	public boolean isPasswordMatch(Password rawPassword, PasswordEncoder encoder) {
		return this.password.matches(rawPassword, encoder);
	}

	public static User create(Username userName, Password password, PasswordEncoder encoder){
		Password encodePassword = password.encode(encoder);
		return User.builder()
			.username(userName)
			.password(encodePassword)
			.userRole(UserRole.USER)
			.build();
	}

}
