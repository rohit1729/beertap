package com.codesherpa.beerdispenser.app.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.codesherpa.beerdispenser.app.dtos.BeerDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateBeerDto;
import com.codesherpa.beerdispenser.app.models.Beer;
import com.codesherpa.beerdispenser.app.repositories.BeerRepository;

public class BeerControllerTests {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BeerRepository beerRepository;
    
    @Test
    public void testCreateBeer() throws Exception{
    CreateBeerDto request = new CreateBeerDto("", BigDecimal.TEN);
    
    ResponseEntity<String> response = restTemplate.postForEntity("/beers", request, String.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    JSONArray errors = new JSONArray(response.getBody());
    assertEquals("Beer name should not be blank", errors.getJSONObject(0).getString("message"));

    request = new CreateBeerDto("Stout", BigDecimal.TEN);  
    ResponseEntity<BeerDto> createdResponse = restTemplate.postForEntity("/beers", request, BeerDto.class);

    assertEquals(HttpStatus.CREATED, createdResponse.getStatusCode());
    Beer saved = beerRepository.findById(createdResponse.getBody().getId()).get();
    assertEquals("Stout", saved.getName()); 
  }

  @Test
  public void testGetBeers() {
    // Create beers

    ResponseEntity<String> response = restTemplate.getForEntity("/beers", String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
