package com.akhil.recipes.commands;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {

	private Long id;
	@NotBlank
	@NotEmpty
	private String description;
	private Long recipeId;
	@Positive
	@NotNull
	private BigDecimal amount;
	private UnitOfMeasureCommand unitOfMeasure;

}
