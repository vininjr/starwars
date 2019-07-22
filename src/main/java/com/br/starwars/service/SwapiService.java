package com.br.starwars.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.net.URL;
import java.net.URLConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.br.starwars.exception.ConnectionException;
import com.br.starwars.exception.PlanetNotFoundException;
import com.br.starwars.model.Planet;
import com.br.starwars.model.PlanetSwapiSearch;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SwapiService {
	@Autowired
	private RestTemplate restTemplate;

	@Value("${swapi.BASE_URL:}")
	private String BASE_URL;

	public List<Planet> getPlanets() throws ConnectionException {
		if (!checkConnect())
			throw new ConnectionException("Not connection detection");

		ResponseEntity<PlanetSwapiSearch> planet = restTemplate.getForEntity(BASE_URL + "/planets/", PlanetSwapiSearch.class);
		return Arrays.asList(planet.getBody().getResults());
	}

	public Planet getPlanetById(String id) throws RestClientException, PlanetNotFoundException, ConnectionException {
		if (!checkConnect())
			throw new ConnectionException("Not connection detection");
		Planet apiPlanet = restTemplate.getForObject(BASE_URL + "/planets/" + id, Planet.class);

		if (apiPlanet == null)
			throw new PlanetNotFoundException("No such planet on the star wars api, check the name you've input");

		return apiPlanet;
	}

	public Planet getPlanetByName(String name)
			throws RestClientException, PlanetNotFoundException, ConnectionException {
		if (!checkConnect())
			throw new ConnectionException("Not connection detection");
		Planet apiPlanet = restTemplate.getForObject(BASE_URL + "/planets?search=" + name, Planet.class);

		if (apiPlanet == null)
			throw new PlanetNotFoundException("No such planet on the star wars api, check the name you've input");

		return apiPlanet;
	}

	public int countFilmsByPlanet(String planetName) throws IOException {
		if (!checkConnect())
			throw new ConnectionException("Not connection detection");

		HttpHeaders headers = new HttpHeaders();
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

		ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/planets?search=" + planetName,
				HttpMethod.GET, entity, String.class);

		JsonNode root = new ObjectMapper().readTree(response.getBody());

		if (root != null && root.get("count").asLong() > 0) {
			JsonNode results = root.get("results");
			JsonNode planet = results.get(0);
			JsonNode films = planet.get("films");
			return films.size();
		}

		return 0;
	}

	private boolean checkConnect() {
		try {
			URL url = new URL(BASE_URL);
			URLConnection connection = url.openConnection();
			connection.connect();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
