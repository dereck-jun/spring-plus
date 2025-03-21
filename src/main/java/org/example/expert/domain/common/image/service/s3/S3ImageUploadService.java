package org.example.expert.domain.common.image.service.s3;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.common.image.service.ImageUploadService;
import org.example.expert.domain.common.image.util.ImageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.util.MimeTypeUtils.IMAGE_JPEG_VALUE;
import static org.springframework.util.MimeTypeUtils.IMAGE_PNG_VALUE;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class S3ImageUploadService implements ImageUploadService {

	private final S3Client s3Client;
	private final ImageUtil imageUtil;

	@Value("${spring.cloud.aws.s3.bucket}")
	public String bucket;

	@Override
	public String upload(MultipartFile originalImage, String email) {
		String contentType = originalImage.getContentType();
		checkFileType(contentType);

		String filename = originalImage.getOriginalFilename();

		String key = imageUtil.generateImageKey(filename, email);

		PutObjectRequest request = PutObjectRequest.builder()
			.bucket(bucket)
			.key(key)
			.contentType(contentType)
			.build();

		try {
			s3Client.putObject(request,
				RequestBody.fromInputStream(originalImage.getInputStream(), originalImage.getSize()));
		} catch (IOException e) {
			log.error("[IOException]  occurring while uploading images. cause: {}", e.getLocalizedMessage());
			throw new InvalidRequestException("이미지 업로드 과정 중 예외가 발생했습니다.");
		}

		return key;
	}

	private void checkFileType(String contentType) {
		if (!IMAGE_PNG_VALUE.equals(contentType) && !IMAGE_JPEG_VALUE.equals(contentType)) {
			throw new InvalidRequestException("파일이 이미지가 아닙니다.");
		}
	}
}
