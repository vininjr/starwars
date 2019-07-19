package com.br.starwars.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Planet {
	@Id
	private String id;
	private String name;
	private String climate;
	private String terrain;
	private Integer filmsCount;

	public Planet() {

	}

	public Planet(String name, String climate, String terrain) {
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String clima) {
		this.climate = clima;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terreno) {
		this.terrain = terreno;
	}

	public Integer getFilmsCount() {
		return filmsCount;
	}

	public void setFilmsCount(Integer filmsCount) {
		this.filmsCount = filmsCount;
	}

	@Override
	public String toString() {
		return "SwapiPlanet [id=" + id + ", name=" + name + ", climate=" + climate + ", terrain=" + terrain
				+ ", filmsCount=" + filmsCount + "]";
	}

}
