package com.akhil.recipes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akhil.recipes.service.RecipeService;

@Controller
public class IndexController {

	private RecipeService recipeService;

	public IndexController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	@RequestMapping({ "", "/" })
	public String getIndexPage(Model model) {
		model.addAttribute("recipes", recipeService.getRecipes());
		return "index";
	}

}
