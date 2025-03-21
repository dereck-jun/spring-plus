package org.example.expert.domain.common.image.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.image.dto.response.ImageUploadResponse;
import org.example.expert.domain.common.image.service.s3.S3ImageUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

	private final S3ImageUploadService imageUploadService;

	@PostMapping
	public ResponseEntity<ImageUploadResponse> upload(
		@RequestPart MultipartFile image,
		@AuthenticationPrincipal AuthUser authUser
	) {
		String imageUrl = imageUploadService.upload(image, authUser.getEmail());
		return ResponseEntity.ok(new ImageUploadResponse(imageUrl));
	}
}
