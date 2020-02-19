package com.akhil.recipes.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.akhil.recipes.commands.IngredientCommand;
import com.akhil.recipes.model.Ingredient;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

	private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

	public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
		super();
		this.uomConverter = uomConverter;
	}

	@Override
	public Ingredient convert(IngredientCommand source) {
		if (source == null) {
			return null;
		}

		final Ingredient ingredient = new Ingredient();
		ingredient.setId(source.getId());
		ingredient.setAmount(source.getAmount());
		ingredient.setDescription(source.getDescription());
		ingredient.setUnitOfMeasure(uomConverter.convert(source.getUnitOfMeasure()));
		return ingredient;
	}

}
