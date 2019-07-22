package com.br.starwars.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.br.starwars.model.Planet;

public interface PlanetRepository extends MongoRepository<Planet, Long> {
	Planet findById(long id);

	Planet findByName(String name);

	Planet findByNameIgnoreCase(String name);

}
