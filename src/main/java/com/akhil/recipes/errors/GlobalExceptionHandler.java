package com.akhil.recipes.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.akhil.recipes.exceptions.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NumberFormatException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ModelAndView handleNumerFormatException() {
		log.debug("handling 400 Bad Request");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("400badrequest");
		return modelAndView;
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView notFound() {

		log.debug("handling 404 Not Found");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("404notfound");
		return modelAndView;

	}

}
