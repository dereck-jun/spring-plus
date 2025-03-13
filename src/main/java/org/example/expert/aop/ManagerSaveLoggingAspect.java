package org.example.expert.aop;

import java.util.UUID;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.log.service.LogService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class ManagerSaveLoggingAspect {

	private final HttpServletRequest request;
	private final LogService logService;

	@Around("execution(* org.example.expert.domain.manager.controller.ManagerController.saveManager(..))")
	public Object logAroundSaveManager(ProceedingJoinPoint joinPoint) throws Throwable {
		String traceId = UUID.randomUUID().toString().substring(0, 7);
		Long userId = getPrincipalByAuthentication().getId();
		String requestURI = request.getRequestURI();
		String method = request.getMethod();
		Object result = null;

		logService.recordRequestLog(traceId, userId, method, requestURI);

		try {
			result = joinPoint.proceed();
			logService.recordSuccessfulLog(traceId, userId, method, requestURI, HttpStatus.OK.name());
		} catch (InvalidRequestException ire) {
			logService.recordFailedLog(traceId, userId, method, requestURI, HttpStatus.BAD_REQUEST.name(), ire.getMessage());
			throw ire;
		}

		return result;
	}

	private AuthUser getPrincipalByAuthentication() {
		return (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
