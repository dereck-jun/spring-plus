package org.example.expert.domain.todo.repository;

import static org.example.expert.domain.comment.entity.QComment.*;
import static org.example.expert.domain.manager.entity.QManager.*;
import static org.example.expert.domain.todo.entity.QTodo.*;
import static org.example.expert.domain.user.entity.QUser.*;
import static org.springframework.util.StringUtils.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TodoRepositoryCustomImpl implements TodoRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<Todo> findByIdWithUser(Long todoId) {
		Todo findTodo = queryFactory
			.select(todo)
			.from(todo)
			.leftJoin(todo.user, user).fetchJoin()
			.where(todo.id.eq(todoId))
			.fetchOne();
		return Optional.ofNullable(findTodo);
	}

	@Override
	public Page<TodoSearchResponse> searchTodos(String title, String managerName, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable) {
		List<TodoSearchResponse> contents = queryFactory
			.select(Projections.constructor(TodoSearchResponse.class,
				todo.id,
				todo.title,
				manager.countDistinct(),
				comment.countDistinct()))
			.from(todo)
			.leftJoin(todo.managers, manager).on(todo.id.eq(manager.todo.id))
			.leftJoin(todo.comments, comment).on(todo.id.eq(comment.todo.id))
			.where(
				titleContains(title),
				managerNameContains(managerName),
				createdAtBetween(startDateTime, endDateTime))
			.groupBy(todo.id, todo.title)
			.orderBy(todo.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(todo.count())
			.from(todo);

		return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
	}

	private BooleanExpression titleContains(String title) {
		return hasText(title) ? todo.title.contains(title) : null;
	}

	private BooleanExpression managerNameContains(String managerName) {
		return hasText(managerName) ? manager.user.nickname.contains(managerName) : null;
	}

	private BooleanExpression createdAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		if (startDateTime == null && endDateTime == null) {
			return null;
		}

		if (startDateTime != null && endDateTime != null) {
			return todo.createdAt.between(startDateTime, endDateTime);
		}

		if (startDateTime != null) {
			return todo.createdAt.after(startDateTime);
		}

		return todo.createdAt.before(endDateTime);
	}
}
