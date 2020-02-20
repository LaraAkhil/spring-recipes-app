package com.akhil.recipes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akhil.recipes.commands.RecipeCommand;
import com.akhil.recipes.model.Recipe;
import com.akhil.recipes.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	@GetMapping("/{id}")
	public String getRecipe(@PathVariable String id, Model model) {
		Recipe recipe = recipeService.findById(Long.valueOf(id));
		model.addAttribute("recipe", recipe);
		return "recipe";
	}

	@GetMapping("/new")
	public String getNewRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return "recipeform";
	}

	@PostMapping("/new")
	public String postNewRecipe(@ModelAttribute RecipeCommand recipeCommand) {
		RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
		return "redirect:/recipe/" + savedRecipeCommand.getId();

	}

	@GetMapping("/{id}/update")
	public String getUpdateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.valueOf(id)));
		return "recipeform";
	}

	@GetMapping("/{id}/delete")
	public String deleteById(@PathVariable String id) {

		recipeService.deleteById(Long.valueOf(id));
		log.debug("deleted recipe with id:" + id);
		return "redirect:/";
	}

}
