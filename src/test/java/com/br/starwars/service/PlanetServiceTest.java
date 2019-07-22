package com.br.starwars.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.br.starwars.exception.PlanetDuplicationInDatabaseException;
import com.br.starwars.exception.PlanetNotFoundException;
import com.br.starwars.model.Planet;
import com.br.starwars.repository.PlanetRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanetServiceTest {

	@Autowired
	private PlanetService service;

	@MockBean
	private PlanetRepository repository;

	private Long mockPlanetId;

	private Planet mockPlanet;

	@Before
	public void before() {
		mockPlanetId = 1L;
		mockPlanet = new Planet();
		mockPlanet.setName("Arya");
		mockPlanet.setClimate("arid");
		mockPlanet.setTerrain("desert");
	}

	@Test
	public void listAllPlanets() {
		List<Planet> planets = service.list();
		assertThat(planets).isNotNull();
	}

	@Test
	public void createPlanetSuccessfully() throws Exception {
		when(repository.findByName(any())).thenReturn(new Planet());
		when(repository.save(mockPlanet)).thenReturn(mockPlanet);
		Planet newPlanet = service.add(mockPlanet);
		assertThat(newPlanet).isNotNull();
	}

	@Test(expected = PlanetDuplicationInDatabaseException.class)
	public void createPlanetWithExistingNameShouldThrowAlreadyExistsException() throws Exception {
		when(repository.findByName(mockPlanet.getName())).thenReturn(mockPlanet);
		service.add(mockPlanet);
	}

	@Test
	public void getPlanetById() throws Exception {
		when(repository.findById(mockPlanetId)).thenReturn(Optional.of(mockPlanet));
		Planet planetFound = service.findById(mockPlanetId);
		assertThat(planetFound).isNotNull();
	}

	@Test(expected = PlanetNotFoundException.class)
	public void getPlanetWithNonExistingIdShouldThrowNotFoundException() throws Exception {
		when(repository.findById(any())).thenReturn(Optional.empty());
		service.findById(mockPlanetId);
	}

	@Test
	public void getPlanetByName() throws Exception {
		when(repository.findByName(mockPlanet.getName())).thenReturn(mockPlanet);
		Planet planetFound = service.findByName(mockPlanet.getName());
		assertThat(planetFound).isNotNull();
		assertThat(planetFound.getName()).isEqualTo(mockPlanet.getName());
	}

	@Test
	public void deletePlanetSuccessfully() throws Exception {
		when(repository.findById(mockPlanetId)).thenReturn(Optional.of(mockPlanet));
		doNothing().when(repository).delete(mockPlanet);
		service.delete(mockPlanetId);
	}

	@Test(expected = PlanetNotFoundException.class)
	public void deletePlanetWithNonExistingIdShouldThrowNotFoundException() throws Exception {
		when(repository.findById(any())).thenReturn(Optional.empty());
		service.delete(mockPlanetId);
	}

}
