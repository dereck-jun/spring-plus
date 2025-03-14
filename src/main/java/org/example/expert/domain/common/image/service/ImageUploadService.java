package org.example.expert.domain.common.image.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {

	String upload(MultipartFile originalImage, String email);
}
