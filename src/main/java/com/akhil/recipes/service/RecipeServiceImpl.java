package com.akhil.recipes.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.akhil.recipes.commands.RecipeCommand;
import com.akhil.recipes.converters.RecipeCommandToRecipe;
import com.akhil.recipes.converters.RecipeToRecipeCommand;
import com.akhil.recipes.exceptions.NotFoundException;
import com.akhil.recipes.model.Recipe;
import com.akhil.recipes.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeToRecipeCommand recipeToRecipeCommand;
	private final RecipeCommandToRecipe recipeCommandToRecipe;

	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToRecipeCommand recipeToRecipeCommand,
			RecipeCommandToRecipe recipeCommandToRecipe) {
		super();
		this.recipeRepository = recipeRepository;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
	}

	@Override
	public Set<Recipe> getRecipes() {
		Set<Recipe> recipeSet = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
		return recipeSet;
	}

	@Override
	public Recipe findById(Long id) {
		Optional<Recipe> opionalRecipe = recipeRepository.findById(id);

		if (opionalRecipe.isEmpty()) {
			throw new NotFoundException("No recipe found with id:" + id);
		}
		Recipe recipe = opionalRecipe.get();

		return recipe;
	}

	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {
		Recipe recipe = recipeRepository.save(recipeCommandToRecipe.convert(command));
		log.debug("Saved RecipeId:" + recipe.getId());
		return recipeToRecipeCommand.convert(recipe);
	}

	@Override
	@Transactional
	public RecipeCommand findRecipeCommandById(Long id) {
		Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
		if (optionalRecipe.isEmpty()) {
			throw new NotFoundException("No recipe found with id:" + id);
		}
		Recipe recipe = optionalRecipe.get();
		return recipeToRecipeCommand.convert(recipe);
	}

	@Override
	public void deleteById(Long id) {
		recipeRepository.deleteById(id);
	}

}
