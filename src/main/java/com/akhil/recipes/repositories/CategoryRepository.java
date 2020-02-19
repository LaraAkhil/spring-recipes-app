package com.akhil.recipes.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.akhil.recipes.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
	
	Optional<Category> findByDescription(String description);
	

}
