package com.akhil.recipes.service;

import java.util.Set;

import com.akhil.recipes.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {

	Set<UnitOfMeasureCommand> getAllUoms();

}
