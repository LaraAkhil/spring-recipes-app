package com.akhil.recipes.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.akhil.recipes.commands.CategoryCommand;
import com.akhil.recipes.converters.CategoryToCategoryCommand;
import com.akhil.recipes.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final CategoryToCategoryCommand categoryToCategoryCommand;

	public CategoryServiceImpl(CategoryRepository categoryRepository,
			CategoryToCategoryCommand categoryToCategoryCommand) {
		super();
		this.categoryRepository = categoryRepository;
		this.categoryToCategoryCommand = categoryToCategoryCommand;
	}

	@Override
	public Set<CategoryCommand> getAllCategoryCommands() {

		Set<CategoryCommand> categories = new HashSet<CategoryCommand>();
		categoryRepository.findAll().iterator()
				.forEachRemaining(e -> categories.add(categoryToCategoryCommand.convert(e)));

		return categories;
	}

}
