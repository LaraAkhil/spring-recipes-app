package com.akhil.recipes.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Notes {

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	@MapsId
	private Recipe recipe;
	@Lob
	private String recipeNotes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public String getRecipeNotes() {
		return recipeNotes;
	}

	public void setRecipeNotes(String recipeNotes) {
		this.recipeNotes = recipeNotes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((recipeNotes == null) ? 0 : recipeNotes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notes other = (Notes) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (recipeNotes == null) {
			if (other.recipeNotes != null)
				return false;
		} else if (!recipeNotes.equals(other.recipeNotes))
			return false;
		return true;
	}

}
