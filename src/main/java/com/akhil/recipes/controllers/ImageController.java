package com.akhil.recipes.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.akhil.recipes.commands.RecipeCommand;
import com.akhil.recipes.service.ImageService;
import com.akhil.recipes.service.RecipeService;

@Controller
@RequestMapping("/recipe")
public class ImageController {

	private final RecipeService recipeService;
	private final ImageService imageService;

	public ImageController(RecipeService recipeService, ImageService imageService) {
		super();
		this.recipeService = recipeService;
		this.imageService = imageService;
	}

	@GetMapping("/{recipeId}/image")
	public String getImageForm(@PathVariable String recipeId, Model model) {

		model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.valueOf(recipeId)));

		return "imageform";

	}

	@PostMapping("/{recipeId}/image")
	public String postImageForm(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
		imageService.saveImage(Long.valueOf(recipeId), file);
		return "redirect:/recipe/" + recipeId;

	}

	@GetMapping("/{recipeId}/recipeImage")
	public void getRecipeImage(@PathVariable String recipeId, HttpServletResponse res) throws IOException {

		RecipeCommand recipe = recipeService.findRecipeCommandById(Long.valueOf(recipeId));
		if (recipe.getImage() == null) {
			// todo error
			return;
		}
		byte[] image = new byte[recipe.getImage().length];

		int i = 0;
		for (byte b : recipe.getImage()) {
			image[i++] = b;
		}

		res.setContentType("image/jpeg");
		InputStream is = new ByteArrayInputStream(image);
		IOUtils.copy(is, res.getOutputStream());

	}

}
