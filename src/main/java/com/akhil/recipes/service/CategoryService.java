package com.akhil.recipes.service;

import java.util.Set;

import com.akhil.recipes.commands.CategoryCommand;

public interface CategoryService {

	public Set<CategoryCommand> getAllCategoryCommands();

}
