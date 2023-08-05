package com.codesherpa.beerdispenser.app.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import com.codesherpa.beerdispenser.app.dtos.AttendeeDto;
import com.codesherpa.beerdispenser.app.dtos.BeerDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateAttendeeDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateBeerDto;
import com.codesherpa.beerdispenser.app.models.Beer;
import com.codesherpa.beerdispenser.app.repositories.BeerRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class BeersControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BeerRepository beerRepository;

    @Test
    public void testCreateBeer() throws Exception {
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
    public void testGetBeers() throws Exception {
        CreateBeerDto beer1Request = new CreateBeerDto("IPA", BigDecimal.valueOf(5));
        ResponseEntity<BeerDto> beer1Response = restTemplate.postForEntity("/beers", beer1Request, BeerDto.class);

        CreateBeerDto beer2Request = new CreateBeerDto("Stout", BigDecimal.valueOf(6));
        ResponseEntity<BeerDto> beer2Response = restTemplate.postForEntity("/beers", beer2Request, BeerDto.class);

        // Fetch all beers
        ResponseEntity<String> response = restTemplate.getForEntity("/beers", String.class);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());

        JSONArray beers = new JSONArray(response.getBody());
        assertEquals(2, beers.length());

        JSONObject beer1Json = beers.getJSONObject(0);
        assertEquals(beer1Response.getBody().getId(), beer1Json.getLong("id"));
        assertEquals(beer1Request.getName(), beer1Json.getString("name"));

        JSONObject beer2Json = beers.getJSONObject(1);
        assertEquals(beer2Response.getBody().getId(), beer2Json.getLong("id"));
        assertEquals(beer2Request.getName(), beer2Json.getString("name"));
    }

    @Test
    public void testGetBeerById() throws Exception {

        // Create test beers
        CreateBeerDto beer1Request = new CreateBeerDto("IPA", BigDecimal.TEN);
        ResponseEntity<BeerDto> beer1Response = restTemplate.postForEntity("/beers", beer1Request, BeerDto.class);
        Long beer1Id = beer1Response.getBody().getId();

        CreateBeerDto beer2Request = new CreateBeerDto("Stout", BigDecimal.TEN);
        ResponseEntity<BeerDto> beer2Response = restTemplate.postForEntity("/beers", beer2Request, BeerDto.class);
        Long beer2Id = beer2Response.getBody().getId();

        // Check invalid beer id
        ResponseEntity<String> invalidResponse = restTemplate.getForEntity("/beers/0", String.class);
        assertEquals(HttpStatus.BAD_REQUEST, invalidResponse.getStatusCode());

        // Check non-existing beer id
        ResponseEntity<String> notFoundResponse = restTemplate.getForEntity("/beers/999", String.class);
        assertEquals(HttpStatus.NOT_FOUND, notFoundResponse.getStatusCode());

        // Get beer 1
        ResponseEntity<BeerDto> beer1GetResponse = restTemplate.getForEntity("/beers/" + beer1Id, BeerDto.class);
        assertEquals(HttpStatus.OK, beer1GetResponse.getStatusCode());
        assertEquals(beer1Id, beer1GetResponse.getBody().getId());

        // Get beer 2
        ResponseEntity<BeerDto> beer2GetResponse = restTemplate.getForEntity("/beers/" + beer2Id, BeerDto.class);
        assertEquals(HttpStatus.OK, beer2GetResponse.getStatusCode());
        assertEquals(beer2Id, beer2GetResponse.getBody().getId());
    }

    @Test
    public void testDeleteBeer() throws Exception {

        // Create a test beer
        CreateBeerDto request = new CreateBeerDto("IPA", BigDecimal.TEN);
        ResponseEntity<BeerDto> createResponse = restTemplate.postForEntity("/beers", request, BeerDto.class);
        System.out.println("id of new beer "+createResponse.getBody().getId());
        // Delete the beer
        Long beerId = createResponse.getBody().getId();
        ResponseEntity<String> deleteResponse = restTemplate.exchange("/beers/" + beerId, HttpMethod.DELETE, null,
                String.class);

        // Verify delete succeeds
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertEquals(beerId.toString(), deleteResponse.getBody());

        // Fetch deleted beer
        ResponseEntity<String> getResponse = restTemplate.getForEntity("/beers/" + beerId, String.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());

    }

    @Test
    public void testDeleteInvalidBeer() throws Exception {

        // Try to delete a non-existing beer
        ResponseEntity<String> deleteResponse = restTemplate.exchange("/beers/999", HttpMethod.DELETE, null,
                String.class);

        JSONArray errors = new JSONArray(deleteResponse.getBody());
        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());
        assertEquals("Beer not found for beerId ", errors.getJSONObject(0).getString("message"));

    }

    @Test
    public void testDeleteWithInvalidId() throws Exception {

        // Try to delete with invalid id
        ResponseEntity<String> deleteResponse = restTemplate.exchange("/beers/0", HttpMethod.DELETE, null,
                String.class);

        JSONArray errors = new JSONArray(deleteResponse.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, deleteResponse.getStatusCode());
        assertEquals("BeerId should be greater than 0", errors.getJSONObject(0).getString("message"));

    }
}
