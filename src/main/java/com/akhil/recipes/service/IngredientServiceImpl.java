package com.akhil.recipes.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akhil.recipes.commands.IngredientCommand;
import com.akhil.recipes.converters.IngredientCommandToIngredient;
import com.akhil.recipes.converters.IngredientToIngredientCommand;
import com.akhil.recipes.exceptions.NotFoundException;
import com.akhil.recipes.model.Ingredient;
import com.akhil.recipes.model.Recipe;
import com.akhil.recipes.repositories.RecipeRepository;
import com.akhil.recipes.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;

	public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient) {
		super();
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngId(Long recipeId, Long ingId) {
		Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
		if (optionalRecipe.isEmpty()) {
			throw new NotFoundException("recipe not found with id: " + recipeId);
		}
		Recipe recipe = optionalRecipe.get();

		Optional<Ingredient> optionalIng = recipe.getIngredients().stream().filter(e -> e.getId().equals(ingId))
				.findFirst();
		if (optionalIng.isEmpty()) {
			throw new NotFoundException();
		}
		return ingredientToIngredientCommand.convert(optionalIng.get());
	}

	@Override
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {

		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

		if (!recipeOptional.isPresent()) {

			log.error("Recipe not found for id: " + command.getRecipeId());
			throw new NotFoundException();
		} else {
			Recipe recipe = recipeOptional.get();

			Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();

			if (ingredientOptional.isPresent()) {
				Ingredient ingredientFound = ingredientOptional.get();
				ingredientFound.setDescription(command.getDescription());
				ingredientFound.setAmount(command.getAmount());
				System.out.println(command.getUnitOfMeasure());
				ingredientFound.setUnitOfMeasure(unitOfMeasureRepository.findById(command.getUnitOfMeasure().getId())
						.orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); // todo address this
			} else {
				// add new Ingredient
				Ingredient ingredient = ingredientCommandToIngredient.convert(command);
				ingredient.setRecipe(recipe);
				recipe.addIngredient(ingredient);
			}

			Recipe savedRecipe = recipeRepository.save(recipe);

			Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
					.filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId())).findFirst();

			// check by description
			if (!savedIngredientOptional.isPresent()) {
				// not totally safe... But best guess
				savedIngredientOptional = savedRecipe.getIngredients().stream()
						.filter(recipeIngredients -> recipeIngredients.getDescription()
								.equals(command.getDescription()))
						.filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
						.filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId()
								.equals(command.getUnitOfMeasure().getId()))
						.findFirst();
			}

			// to do check for fail
			return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
		}

	}

	@Override
	@Transactional
	public void deleteById(Long recipeId, Long ingId) {

		Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

		if (optionalRecipe.isEmpty()) {

			log.error("recipe not found with id: " + recipeId);
			throw new NotFoundException();

		} else {
			Recipe recipe = optionalRecipe.get();
			Optional<Ingredient> optionalIng = recipe.getIngredients().stream().filter(e -> e.getId().equals(ingId))
					.findFirst();
			if (optionalIng.isEmpty()) {

				log.error("ing not found");
				throw new NotFoundException();
			} else {
				Ingredient ing = optionalIng.get();
				recipe.removeIngredient(ing);

				recipeRepository.save(recipe);

			}
		}

	}

}
