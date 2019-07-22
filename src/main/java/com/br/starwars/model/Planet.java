package com.br.starwars.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "planet")
public class Planet {
	@Transient
	public static final String SEQUENCE_NAME = "planet_sequence";

	@Id
	private Long id;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
