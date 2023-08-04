package com.codesherpa.beerdispenser.app.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.codesherpa.beerdispenser.app.dtos.request.CreatePromoterDto;
import com.codesherpa.beerdispenser.app.models.Beer;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class BeerControllerTests {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void testGetBeers() {
    ResponseEntity<Beer[]> response = restTemplate.getForEntity("/beers", Beer[].class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
public void testCreatePromoter() {

  CreatePromoterDto request = new CreatePromoterDto("John", true);
  
  ResponseEntity<PromoterDto> response = restTemplate.postForEntity("/promoters", request, PromoterDto.class);

  assertEquals(HttpStatus.CREATED, response.getStatusCode());

  Promoter saved = promoterRepository.findById(response.getBody().getId()).get();
  assertEquals("John", saved.getName());
}

}
