package org.example.expert.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.dto.request.UserChangeProfileRequest;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/users/{userId}")
	public ResponseEntity<UserResponse> getUser(@PathVariable long userId) {
		return ResponseEntity.ok(userService.getUser(userId));
	}

	@PutMapping("/users")
	public void changePassword(
		@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody UserChangePasswordRequest userChangePasswordRequest
	) {
		userService.changePassword(authUser.getId(), userChangePasswordRequest);
	}

	@PatchMapping("/users")
	public void changeProfileImage(
		@AuthenticationPrincipal AuthUser authUser,
		@RequestBody UserChangeProfileRequest request
	) {
		userService.changeProfileImage(authUser.getId(), request);
	}

	@GetMapping("/users")
	public ResponseEntity<Page<UserResponse>> getUsers(@RequestParam String nickname, Pageable pageable) {
		return ResponseEntity.ok(userService.getUsersByNickname(nickname, pageable));
	}
}
