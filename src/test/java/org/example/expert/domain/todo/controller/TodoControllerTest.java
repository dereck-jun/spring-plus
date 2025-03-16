package org.example.expert.domain.todo.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.example.expert.config.JwtAuthenticationFilter;
import org.example.expert.config.JwtUtil;
import org.example.expert.config.PropertyConfig;
import org.example.expert.config.WithMockAuthUser;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.service.TodoService;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

@Import({PropertyConfig.class, JwtUtil.class, JwtAuthenticationFilter.class})
@WebMvcTest(TodoController.class)
class TodoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TodoService todoService;

	@Test
	@WithMockAuthUser(userId = 1L, email = "a@a.com", role = UserRole.USER)
	void todo_단건_조회에_성공한다() throws Exception {
		// given
		long todoId = 1L;
		String title = "title";
		AuthUser authUser = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = User.fromAuthUser(authUser);
		UserResponse userResponse = new UserResponse(user.getId(), user.getEmail());
		LocalDateTime now = LocalDateTime.now();
		String formatted = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS"));
		TodoResponse response = new TodoResponse(
			todoId,
			title,
			"contents",
			"Sunny",
			userResponse,
			now,
			now
		);

		given(todoService.getTodo(anyLong())).willReturn(response);

		mockMvc.perform(get("/todos/{todoId}", todoId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(todoId))
			.andExpect(jsonPath("$.title").value(title))
			.andExpect(jsonPath("$.contents").value("contents"))
			.andExpect(jsonPath("$.weather").value("Sunny"))
			.andExpect(jsonPath("$.user.id").value(userResponse.getId()))
			.andExpect(jsonPath("$.user.email").value(userResponse.getEmail()))
			.andExpect(jsonPath("$.createdAt").value(formatted))
			.andExpect(jsonPath("$.modifiedAt").value(formatted));
	}

	@Test
	@WithMockAuthUser(userId = 1L, email = "a@a.com", role = UserRole.USER)
	void todo_단건_조회_시_todo가_존재하지_않아_예외가_발생한다() throws Exception {
		// given
		long todoId = 1L;

		// when
		when(todoService.getTodo(todoId))
			.thenThrow(new InvalidRequestException("Todo not found"));

		// then
		mockMvc.perform(get("/todos/{todoId}", todoId))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
			.andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
			.andExpect(jsonPath("$.message").value("Todo not found"));
	}
}
