package org.example.expert.domain.log.entity;

import org.example.expert.domain.common.entity.Timestamped;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "log")
@NoArgsConstructor
public class Log extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, updatable = false)
	private Long userId;

	@Column(nullable = false, updatable = false)
	private String traceId;

	@Column(nullable = false, updatable = false)
	private String method;

	@Column(nullable = false, updatable = false)
	private String requestUri;

	@Column(updatable = false)
	private String errorMessage;

	@Column(updatable = false)
	private String httpStatus;

	// 요청 로그
	public Log(String traceId, Long userId, String method, String requestUri) {
		this.traceId = traceId;
		this.userId = userId;
		this.method = method;
		this.requestUri = requestUri;
	}

	// 요청 성공 시 로그
	public Log(String traceId, Long userId, String method, String requestUri, String httpStatus) {
		this.traceId = traceId;
		this.userId = userId;
		this.method = method;
		this.requestUri = requestUri;
		this.httpStatus = httpStatus;
	}

	// 요청 실패 시 로그
	public Log(String traceId, Long userId, String method, String requestUri, String httpStatus, String errorMessage) {
		this.traceId = traceId;
		this.userId = userId;
		this.method = method;
		this.requestUri = requestUri;
		this.httpStatus = httpStatus;
		this.errorMessage = errorMessage;
	}
}
