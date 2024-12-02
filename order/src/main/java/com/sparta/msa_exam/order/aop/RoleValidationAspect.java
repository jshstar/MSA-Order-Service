package com.sparta.msa_exam.order.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.sparta.msa_exam.order.client.AuthClient;
import com.sparta.msa_exam.order.client.dto.AuthValidationResponse;
import com.sparta.msa_exam.order.common.code.OrderErrorCode;
import com.sparta.msa_exam.order.context.AuthValidationContext;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RoleValidationAspect {

	private final AuthClient authClient;
	private final HttpServletRequest httpServletRequest;

	@Before("@annotation(requiresRole)")
	public void validateRole(RequiresRole requiresRole) {
		String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
		log.info(token);
		if (token == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing required headers");
		}

		AuthValidationResponse response = authClient.validateRole(token);
		String userRole = response.getUserRole();
		String[] requiredRoles = requiresRole.value();

		boolean hasRole = false;
		for (String requiredRole : requiredRoles) {
			if (requiredRole.equals(userRole)) {
				hasRole = true;
				break;
			}
		}
		if (!hasRole) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, OrderErrorCode.INVALID_USER_ROLE.getMessage());
		}

		AuthValidationContext.set(response);
	}

	@After("@annotation(com.sparta.msa_exam.order.aop.RequiresRole)")
	public void clearContext() {
		AuthValidationContext.clear();
		log.info("AuthValidationContext cleared.");
	}
}
