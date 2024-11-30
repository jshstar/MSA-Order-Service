package com.sparta.msa_exam.auth.valueobject;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.sparta.msa_exam.auth.common.code.AuthErrorCode;
import com.sparta.msa_exam.auth.common.exception.AuthException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

	@Column(name = "password", nullable = false)
	private String value;

	public Password(String value) {
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException(new AuthException(AuthErrorCode.PASSWORD_EMPTY));
		}
		this.value = value;
	}

	public Password encode(PasswordEncoder encoder) {
		return new Password(encoder.encode(value));
	}

	public boolean matches(Password rawPassword, PasswordEncoder encoder) {
		return encoder.matches(rawPassword.getValue(), this.value);
	}

	@Override
	public String toString() {
		return "****";
	}


}
