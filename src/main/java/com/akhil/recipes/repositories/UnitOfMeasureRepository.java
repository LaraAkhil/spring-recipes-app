package com.akhil.recipes.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.akhil.recipes.model.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
	
	Optional<UnitOfMeasure> findByDescription(String description);
	

}
