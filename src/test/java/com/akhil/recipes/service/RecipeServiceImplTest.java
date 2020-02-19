package com.akhil.recipes.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.akhil.recipes.converters.RecipeCommandToRecipe;
import com.akhil.recipes.converters.RecipeToRecipeCommand;
import com.akhil.recipes.model.Recipe;
import com.akhil.recipes.repositories.RecipeRepository;

class RecipeServiceImplTest {

	RecipeServiceImpl recipeService;

	@Mock
	RecipeRepository recipeRepository;

	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;

	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository, recipeToRecipeCommand, recipeCommandToRecipe);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetRecipes() {

		Recipe recipe = new Recipe();
		HashSet<Recipe> recipeData = new HashSet<>();
		recipeData.add(recipe);

		when(recipeRepository.findAll()).thenReturn(recipeData);

		Set<Recipe> recipes = recipeService.getRecipes();
		assertNotNull(recipes);
		verify(recipeRepository, times(1)).findAll();
	}

}
