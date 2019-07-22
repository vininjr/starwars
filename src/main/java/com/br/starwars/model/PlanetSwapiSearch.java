package com.br.starwars.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanetSwapiSearch {

	private Integer count;

	private Planet[] results;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Planet[] getResults() {
		return results;
	}

	public void setResults(Planet[] swPlanets) {
		this.results = swPlanets;
	}

}