package org.example.expert.domain.log.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.log.entity.Log;
import org.example.expert.domain.log.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogService {

	private final LogRepository logRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void recordRequestLog(String traceId, Long userId, String httpMethod, String requestUri) {
		logRepository.save(new Log(traceId, userId, httpMethod, requestUri));
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void recordSuccessfulLog(String traceId, Long userId, String httpMethod, String requestUri, String httpStatus) {
		logRepository.save(new Log(traceId, userId, httpMethod, requestUri, httpStatus));
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void recordFailedLog(String traceId, Long userId, String httpMethod, String requestUri, String httpStatus, String errorMessage) {
		logRepository.save(new Log(traceId, userId, httpMethod, requestUri, httpStatus, errorMessage));
	}
}
