package org.example.expert.domain.common.image.util;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class ImageUtil {

	public String generateImageKey(String originalImageName, String email) {
		int extensionIndex = originalImageName.lastIndexOf(".");
		String imageExtension = originalImageName.substring(extensionIndex).toLowerCase();
		String imageName = originalImageName.substring(0, extensionIndex);

		String now = String.valueOf(System.currentTimeMillis());

		String convertEmail = DigestUtils.md5DigestAsHex(email.getBytes());

		// {convertEmail}/{now}_{imageName}.{imageExtension}
		return convertEmail + "/" + now + "_" + imageName + imageExtension;
	}
}
