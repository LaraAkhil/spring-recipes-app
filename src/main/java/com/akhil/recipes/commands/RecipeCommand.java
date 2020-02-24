package com.akhil.recipes.commands;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import com.akhil.recipes.model.Difficulty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

	private Long id;
	@NotBlank
	@Size(min = 3, max = 255)
	private String description;
	@Positive
	@Max(999)
	@Digits(integer = 3, fraction = 0)
	private Integer prepTime;
	@Positive
	@Max(999)
	@Digits(integer = 3, fraction = 0)
	private Integer cookTime;
	@Positive
	@Max(1000)
	@Digits(integer = 4, fraction = 0)
	private Integer servings;
	private String source;
	@URL
	private String url;
	private String directions;
	private Byte[] image;
	private Set<IngredientCommand> ingredients = new HashSet<>();
	private Difficulty difficulty;
	private NotesCommand notes;
	private Set<CategoryCommand> categories = new HashSet<>();
	private String[] categoriesArray = null;

}
