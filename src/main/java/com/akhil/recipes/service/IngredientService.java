package com.akhil.recipes.service;

import com.akhil.recipes.commands.IngredientCommand;

public interface IngredientService {
	IngredientCommand findByRecipeIdAndIngId(Long recipeId, Long ingId);

	IngredientCommand saveIngredientCommand(IngredientCommand command);

	void deleteById(Long recipeId, Long ingId);
}
