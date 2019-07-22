package com.br.starwars.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.br.starwars.model.Planet;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlanetControllerTest {

	@Autowired
	private MockMvc mvc;

	private Planet mockPlanet;

	@Before
	public void before() {
		mockPlanet = new Planet();
		mockPlanet.setName("Sansa");
		mockPlanet.setClimate("arid");
		mockPlanet.setTerrain("desert");
	}

	@Test
	public void createPlanetShouldReturnCreated201() throws Exception {
		mvc.perform(post("/planets").content(toJson(mockPlanet)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	public void createPlanetWithExistingNameShouldReturnConflict409() throws Exception {
		mvc.perform(post("/planets").content(toJson(mockPlanet)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}

	@Test
	public void createPlanetShouldReturnUnsupportedMediaType415() throws Exception {
		mvc.perform(post("/planets").content(toJson(mockPlanet))).andExpect(status().isUnsupportedMediaType());
	}

	@Test
	public void createEmptyPlanetShouldReturnBadRequest400() throws Exception {
		mvc.perform(post("/planets").content("").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void createPlanetWithoutNameShouldReturnBadRequest400() throws Exception {
		mockPlanet.setName(null);
		mvc.perform(post("/planets").content(toJson(mockPlanet)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	private String toJson(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
