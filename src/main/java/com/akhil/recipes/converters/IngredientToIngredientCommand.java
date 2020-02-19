package com.akhil.recipes.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.akhil.recipes.commands.IngredientCommand;
import com.akhil.recipes.model.Ingredient;

import lombok.Synchronized;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

	private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

	public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
		super();
		this.uomConverter = uomConverter;
	}

	@Synchronized
	@Nullable
	@Override
	public IngredientCommand convert(Ingredient source) {
		if (source == null) {
			return null;
		}

		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(source.getId());
		ingredientCommand.setAmount(source.getAmount());
		ingredientCommand.setDescription(source.getDescription());
		ingredientCommand.setUnitOfMeasure(uomConverter.convert(source.getUnitOfMeasure()));
		return ingredientCommand;
	}

}
