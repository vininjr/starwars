package com.br.starwars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PlanetDuplicationInDatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PlanetDuplicationInDatabaseException(String msg) {
		super(msg);
	}
}