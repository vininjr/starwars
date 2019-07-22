package com.br.starwars.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.starwars.exception.ConnectionException;
import com.br.starwars.exception.PlanetAtributeInvalid;
import com.br.starwars.exception.PlanetNotFoundException;
import com.br.starwars.model.Planet;
import com.br.starwars.service.PlanetService;
import com.br.starwars.service.SwapiService;

@RestController
public class PlanetController {

	@Autowired
	private PlanetService planetService;

	@Autowired
	private SwapiService swapiService;

	@GetMapping(value = "/planets")
	public List<Planet> getAllPlanets() throws RestClientException {
		return this.planetService.list();
	}

	@GetMapping(value = "/planets/swapi")
	public List<Planet> getAllPlanetsFromSwapi() throws Exception {
		return this.swapiService.getPlanets();
	}

	@GetMapping(value = "/planets/{id}")
	public Planet getPlanetById(@PathVariable("id") long id) throws RestClientException {
		return this.planetService.findById(id);
	}

	@GetMapping(value = "/planets/search")
	public Planet findByName(@RequestParam(value = "name", required = true) String name) throws RestClientException {
		return this.planetService.findByName(name);
	}

	@PostMapping(value = "/planets")
	public ResponseEntity<Planet> addPlanet(@RequestBody Planet planetToAdd)
			throws RestClientException, PlanetNotFoundException, IOException, PlanetAtributeInvalid {
		Planet newPlanet = planetService.add(planetToAdd);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/planets/{id}")
				.buildAndExpand(newPlanet.getId()).toUri();
		return ResponseEntity.created(location).body(planetToAdd);
	}

	@DeleteMapping(value = "/planets/{id}")
	public void delete(@PathVariable("id") long id) throws RestClientException {
		this.planetService.delete(id);
	}

	@GetMapping("/planets/swapi/{id}")
	public Planet getSWAPIById(@PathVariable("id") String id)
			throws RestClientException, ConnectionException, PlanetNotFoundException {
		return this.swapiService.getPlanetById(id);
	}
}
