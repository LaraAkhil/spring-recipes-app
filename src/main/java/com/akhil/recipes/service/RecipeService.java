package com.akhil.recipes.service;

import java.util.Set;

import com.akhil.recipes.commands.RecipeCommand;
import com.akhil.recipes.model.Recipe;

public interface RecipeService {

	Set<Recipe> getRecipes();

	Recipe findById(Long id);

	RecipeCommand saveRecipeCommand(RecipeCommand command);

}
