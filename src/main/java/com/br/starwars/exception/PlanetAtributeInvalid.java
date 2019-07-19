package com.br.starwars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PlanetAtributeInvalid extends Exception {
	private static final long serialVersionUID = 1L;

	public PlanetAtributeInvalid(String msg) {
		super(msg);
	}

}
