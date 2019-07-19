package com.br.starwars.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.br.starwars.model.Planet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SwapiServiceTest {

	@Autowired
	private SwapiService swapiService;

	@Test
	public void findAllPlanets() throws Exception {
		List<Planet> response = swapiService.getPlanets();
		assertThat(response).isNotEmpty();
	}

	@Test
	public void countFilmsByPlanetTatooineShouldReturn5() throws IOException {
		int countFilms = swapiService.countFilmsByPlanet("Tatooine");
		assertThat(countFilms).isEqualTo(5);
	}

}
