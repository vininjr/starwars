package com.br.starwars.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.br.starwars.exception.PlanetAtributeInvalid;
import com.br.starwars.exception.PlanetDuplicationInDatabaseException;
import com.br.starwars.exception.PlanetNotFoundException;
import com.br.starwars.model.Planet;
import com.br.starwars.repository.PlanetRepository;

@Service
public class PlanetService {

	@Autowired
	private PlanetRepository planetRepository;

	@Autowired
	private SwapiService swapiService;

	public List<Planet> list() {
		return planetRepository.findAll();
	}

	public Planet findById(long id) {
		Planet planet = planetRepository.findById(id);
		if (planet == null)
			throw new PlanetNotFoundException("Planet not found " + id);
		return planet;
	}

	public Planet findByName(String name) {
		Planet planet = planetRepository.findByName(name);
		if (planet == null)
			throw new PlanetNotFoundException("Planet not found" + name);
		return planet;
	}

	public void delete(long id) {
		planetRepository.deleteById(id);
	}

	public Planet add(Planet planetToAdd)
			throws RestClientException, PlanetNotFoundException, IOException, PlanetAtributeInvalid {
		if (Stream.of(planetToAdd, planetToAdd.getName()).anyMatch(Objects::isNull) || planetToAdd.getName().equals(""))
			throw new PlanetAtributeInvalid("Planet not provided in the request");

		// Verify duplicate in the swapi API.
		// Planet apiPlanet = swapiService.getPlanetByName(planetToAdd.getName());

		if (planetRepository.findByName(planetToAdd.getName()) != null) {
			throw new PlanetDuplicationInDatabaseException(
					"Planet name must be unique in the database, check the name you've input");
		}
		planetToAdd.setFilmsCount(swapiService.countFilmsByPlanet(planetToAdd.getName()));

		return planetRepository.save(planetToAdd);
	}
}