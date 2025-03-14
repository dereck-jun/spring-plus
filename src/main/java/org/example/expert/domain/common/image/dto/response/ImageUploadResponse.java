package org.example.expert.domain.common.image.dto.response;

import lombok.Getter;

@Getter
public class ImageUploadResponse {

	private final String imageUrl;

	public ImageUploadResponse(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
