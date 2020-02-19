package com.akhil.recipes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akhil.recipes.model.Recipe;
import com.akhil.recipes.service.RecipeService;

@Controller
public class RecipeController {

	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	@RequestMapping("/recipe/{id}")
	public String getRecipePage(@PathVariable String id, Model model) {
		Recipe recipe = recipeService.findById(Long.valueOf(id));
		model.addAttribute("recipe", recipe);
		return "recipe";
	}

}
