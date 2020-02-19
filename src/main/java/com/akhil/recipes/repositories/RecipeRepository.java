package com.akhil.recipes.repositories;

import org.springframework.data.repository.CrudRepository;

import com.akhil.recipes.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
