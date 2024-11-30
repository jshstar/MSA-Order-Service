package com.sparta.msa_exam.auth.valueobject;

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
public class Username {

	@Column(name = "username", nullable = false, unique = true)
	private String value;

	public Username(String value){
		if(value == null || value.trim().isEmpty()){
			throw new IllegalArgumentException(new AuthException(AuthErrorCode.USERNAME_EMPTY));
		}
		this.value = value;
	}

	@Override
	public String toString(){
		return value;
	}


}
