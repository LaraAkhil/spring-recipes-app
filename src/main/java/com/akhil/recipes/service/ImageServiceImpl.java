package com.akhil.recipes.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.akhil.recipes.model.Recipe;
import com.akhil.recipes.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	private final RecipeRepository recipeRepository;

	public ImageServiceImpl(RecipeRepository recipeRepository) {
		super();
		this.recipeRepository = recipeRepository;
	}

	@Override
	public Void saveImage(Long id, MultipartFile file) {
		log.debug("trying to save img for recipeId: " + id);
		Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
		if (optionalRecipe.isEmpty()) {
			// todo do something

		} else {
			try {
				Recipe recipe = optionalRecipe.get();
				Byte[] bytes = new Byte[file.getBytes().length];
				int i = 0;
				for (byte b : file.getBytes()) {
					bytes[i++] = b; // auto boxing
				}
				recipe.setImage(bytes);
				recipeRepository.save(recipe);
			} catch (Exception e) {
				log.error("error" + e);
				e.printStackTrace();
				// TODO: handle exception
			}
		}
		return null;
	}

}
