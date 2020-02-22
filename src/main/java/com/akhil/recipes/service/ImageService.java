package com.akhil.recipes.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	Void saveImage(Long id, MultipartFile file);

}
