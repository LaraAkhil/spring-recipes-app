package com.akhil.recipes.converters;

import java.util.Optional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.akhil.recipes.commands.RecipeCommand;
import com.akhil.recipes.model.Category;
import com.akhil.recipes.model.Recipe;
import com.akhil.recipes.repositories.CategoryRepository;
import com.akhil.recipes.repositories.RecipeRepository;

import lombok.Synchronized;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

	private final CategoryRepository categoryRepository;
	private final CategoryCommandToCategory categoryConveter;
	private final IngredientCommandToIngredient ingredientConverter;
	private final NotesCommandToNotes notesConverter;
	private final RecipeRepository recipeRepository;

	public RecipeCommandToRecipe(CategoryRepository categoryRepository, CategoryCommandToCategory categoryConveter,
			IngredientCommandToIngredient ingredientConverter, NotesCommandToNotes notesConverter,
			RecipeRepository recipeRepository) {
		super();
		this.categoryRepository = categoryRepository;
		this.categoryConveter = categoryConveter;
		this.ingredientConverter = ingredientConverter;
		this.notesConverter = notesConverter;
		this.recipeRepository = recipeRepository;
	}

	@Synchronized
	@Nullable
	@Override
	public Recipe convert(RecipeCommand source) {
		if (source == null) {
			return null;
		}

		final Recipe recipe = new Recipe();
		recipe.setId(source.getId());
		recipe.setCookTime(source.getCookTime());
		recipe.setPrepTime(source.getPrepTime());
		recipe.setDescription(source.getDescription());
		recipe.setDifficulty(source.getDifficulty());
		recipe.setDirections(source.getDirections());
		recipe.setServings(source.getServings());
		recipe.setSource(source.getSource());
		recipe.setUrl(source.getUrl());
		recipe.setNotes(notesConverter.convert(source.getNotes()));

		if (source.getCategories() != null && source.getCategories().size() > 0) {
			source.getCategories().forEach(category -> recipe.getCategories().add(categoryConveter.convert(category)));
		}

		if (source.getIngredients() != null && source.getIngredients().size() > 0) {
			source.getIngredients()
					.forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
		}

		if (!source.getCategoriesArray().equals(null) && source.getCategoriesArray().length > 0) {

			String[] categoriesArray = source.getCategoriesArray();
			for (String cat : categoriesArray) {
				Optional<Category> optionalCategory = categoryRepository.findById(Long.valueOf(cat));
				if (optionalCategory.isPresent()) {
					Category category = optionalCategory.get();
					recipe.getCategories().add(category);
				}
			}

		} else {
			Optional<Recipe> optionalRecipe = recipeRepository.findById(source.getId());
			if (optionalRecipe.isPresent()) {
				recipe.setCategories(optionalRecipe.get().getCategories());
			}

		}

		return recipe;
	}

}
