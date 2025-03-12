package org.example.expert.domain.todo.dto.response;

import lombok.Getter;

@Getter
public class TodoSearchResponse {

	private final Long todoId;
	private final String title;
	private final Long managerCount;
	private final Long commentCount;

	public TodoSearchResponse(Long todoId, String title, Long managerCount, Long commentCount) {
		this.todoId = todoId;
		this.title = title;
		this.managerCount = managerCount;
		this.commentCount = commentCount;
	}
}
