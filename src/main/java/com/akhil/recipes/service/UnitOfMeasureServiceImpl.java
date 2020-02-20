package com.akhil.recipes.service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.akhil.recipes.commands.UnitOfMeasureCommand;
import com.akhil.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.akhil.recipes.repositories.UnitOfMeasureRepository;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
			UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
		super();
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
	}

	@Override
	public Set<UnitOfMeasureCommand> getAllUoms() {

		return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
				.map(unitOfMeasureToUnitOfMeasureCommand::convert).collect(Collectors.toSet());
	}

}
