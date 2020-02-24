package com.akhil.recipes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akhil.recipes.commands.IngredientCommand;
import com.akhil.recipes.commands.RecipeCommand;
import com.akhil.recipes.commands.UnitOfMeasureCommand;
import com.akhil.recipes.service.IngredientService;
import com.akhil.recipes.service.RecipeService;
import com.akhil.recipes.service.UnitOfMeasureService;

@Controller
@RequestMapping("/recipe")
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService unitOfMeasureService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService,
			UnitOfMeasureService unitOfMeasureService) {
		super();
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
	}

	@GetMapping("/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.valueOf(recipeId)));
		return "ingredients";

	}

	@GetMapping("/{recipeId}/ingredient/{ingId}")
	public String showIngredient(@PathVariable String recipeId, @PathVariable String ingId, Model model) {
		model.addAttribute("ingredient",
				ingredientService.findByRecipeIdAndIngId(Long.valueOf(recipeId), Long.valueOf(ingId)));
		return "ingredient";

	}

	@GetMapping("/{recipeId}/ingredient/{ingId}/update")
	public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingId, Model model) {

		model.addAttribute("ingredient",
				ingredientService.findByRecipeIdAndIngId(Long.valueOf(recipeId), Long.valueOf(ingId)));
		model.addAttribute("uomList", unitOfMeasureService.getAllUoms());
		return "ingredientform";

	}

	@PostMapping("/{recipeId}/ingredient")
	public String saveOrUpdateIngredient(@Validated @ModelAttribute("ingredient") IngredientCommand ingredient,
			BindingResult bindingResult, @PathVariable String recipeId, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("uomList", unitOfMeasureService.getAllUoms());
			return "ingredientform";
		}
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredient);
		return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId();
	}

	@GetMapping("/{recipeId}/ingredient/new")
	public String newIngredient(@PathVariable String recipeId, Model model) {
		RecipeCommand recipeCommand = recipeService.findRecipeCommandById(Long.valueOf(recipeId));
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(Long.valueOf(recipeId));
		model.addAttribute("ingredient", ingredientCommand);

		ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

		model.addAttribute("uomList", unitOfMeasureService.getAllUoms());
		return "ingredientform";
	}

	@GetMapping("{recipeId}/ingredient/{ingId}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingId) {

		ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(ingId));

		return "redirect:/recipe/" + recipeId + "/ingredients";

	}

}
