package com.br.starwars.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.br.starwars.exception.PlanetNotFoundException;
import com.br.starwars.model.Planet;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SwapiService {
	@Autowired
	private RestTemplate restTemplate;

	@Value("${swapi.BASE_URL:}")
	private String BASE_URL;

	public List<Planet> getPlanets() throws Exception {
		ResponseEntity<Planet> planet = restTemplate.getForEntity(BASE_URL + "/planets/", Planet.class);
		return Arrays.asList(planet.getBody());
	}

	public Planet getPlanetById(String id) throws RestClientException, PlanetNotFoundException {
		Planet apiPlanet = restTemplate.getForObject("https://swapi.co/api/planets/" + id, Planet.class);

		if (apiPlanet == null)
			throw new PlanetNotFoundException("No such planet on the star wars api, check the name you've input");

		return apiPlanet;
	}

	public int countFilmsByPlanet(String planetName) throws IOException {
		ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/planets?search=" + planetName,
				HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);

		JsonNode root = new ObjectMapper().readTree(response.getBody());

		if (root != null && root.get("count").asLong() > 0) {
			JsonNode results = root.get("results");
			JsonNode planet = results.get(0);
			JsonNode films = planet.get("films");
			return films.size();
		}

		return 0;
	}
}
