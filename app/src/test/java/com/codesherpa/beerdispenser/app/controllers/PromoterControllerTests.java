package com.codesherpa.beerdispenser.app.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
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

import com.codesherpa.beerdispenser.app.dtos.PromoterDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreatePromoterDto;
import com.codesherpa.beerdispenser.app.models.Promoter;
import com.codesherpa.beerdispenser.app.repositories.PromoterRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class PromoterControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PromoterRepository promoterRepository;

    @Test
    public void testCreatePromoter() throws Exception {
        CreatePromoterDto request = new CreatePromoterDto("", true);
        ResponseEntity<String> error_response = restTemplate.postForEntity("/promoters", request, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, error_response.getStatusCode());
        JSONArray errorsJson = new JSONArray(error_response.getBody());
        assertEquals("Promoter name should not be blank", errorsJson.getJSONObject(0).getString("message"));

        request = new CreatePromoterDto("John", true);
        ResponseEntity<PromoterDto> response = restTemplate.postForEntity("/promoters", request, PromoterDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Promoter saved = promoterRepository.findById(response.getBody().getId()).get();
        assertEquals("John", saved.getName());
    }

    @Test
    public void testGetPromoters() throws Exception {
        CreatePromoterDto request = new CreatePromoterDto("John", true);
        ResponseEntity<PromoterDto> response = restTemplate.postForEntity("/promoters", request, PromoterDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        request = new CreatePromoterDto("Cena", true);
        response = restTemplate.postForEntity("/promoters", request, PromoterDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        ResponseEntity<String> getPromotersResponse = restTemplate.getForEntity("/promoters", String.class);
        assertEquals(getPromotersResponse.getStatusCode(), HttpStatus.OK);

        System.out.println(getPromotersResponse);
        JSONArray responseJson = new JSONArray(getPromotersResponse.getBody());
        assertEquals(responseJson.length(), 2);
        assertEquals(responseJson.getJSONObject(0).get("name"), "John");
    }

    @Test
    public void testGetPromoterById() throws Exception {
        CreatePromoterDto request = new CreatePromoterDto("John", true);
        ResponseEntity<PromoterDto> response = restTemplate.postForEntity("/promoters", request, PromoterDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Long promoterIdJohn = response.getBody().getId();

        request = new CreatePromoterDto("Cena", true);
        response = restTemplate.postForEntity("/promoters", request, PromoterDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Check promoterId validation
        ResponseEntity<String> invalidPromoterIdResponse = restTemplate.getForEntity("/promoters/0", String.class);
        JSONArray invalidPromoterIdResponseJson = new JSONArray(invalidPromoterIdResponse.getBody());
        assertEquals(invalidPromoterIdResponse.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(invalidPromoterIdResponseJson.getJSONObject(0).getString("message"),
                "PromoterId should be greater than 0");

        // Check not found promoter
        ResponseEntity<String> notFoundPromoterResponse = restTemplate.getForEntity("/promoters/10000", String.class);
        JSONArray notFoundPromoterResponseJson = new JSONArray(notFoundPromoterResponse.getBody());
        assertEquals(notFoundPromoterResponse.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(notFoundPromoterResponseJson.getJSONObject(0).getString("message"),
                "Promoter not found for promoterId ");

        ResponseEntity<PromoterDto> getPromoterByIdResponse = restTemplate.getForEntity("/promoters/" + promoterIdJohn,
                PromoterDto.class);
        assertEquals(getPromoterByIdResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(getPromoterByIdResponse.getBody().getName(), "John");
    }

    @Test
    public void testDeletePromoter() throws Exception {

        // Create a promoter
        CreatePromoterDto request = new CreatePromoterDto("John", true);
        ResponseEntity<PromoterDto> response = restTemplate.postForEntity("/promoters", request, PromoterDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Long promoterId = response.getBody().getId();

        // Delete the promoter
        ResponseEntity<Long> deleteResponse = restTemplate.exchange("/promoters/" + promoterId, HttpMethod.DELETE, null,
                Long.class);
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertEquals(promoterId, deleteResponse.getBody());

        // Try to get deleted promoter
        ResponseEntity<String> getDeletedResponse = restTemplate.getForEntity("/promoters/" + promoterId, String.class);
        JSONArray getDeletedJson = new JSONArray(getDeletedResponse.getBody());
        assertEquals(HttpStatus.NOT_FOUND, getDeletedResponse.getStatusCode());

    }

    @Test
    public void testDeleteNotFoundPromoter() throws Exception {

        // Try to delete a non-existing promoter
        ResponseEntity<String> deleteResponse = restTemplate.exchange("/promoters/999", HttpMethod.DELETE, null,
                String.class);
        JSONArray deleteJson = new JSONArray(deleteResponse.getBody());

        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());
        assertEquals("Promoter not found for promoterId ", deleteJson.getJSONObject(0).getString("message"));

    }

    @Test
    public void testDeleteWithInvalidId() throws Exception {

        // Try to delete with invalid id
        ResponseEntity<String> deleteResponse = restTemplate.exchange("/promoters/0", HttpMethod.DELETE, null,
                String.class);
        JSONArray deleteJson = new JSONArray(deleteResponse.getBody());

        assertEquals(HttpStatus.BAD_REQUEST, deleteResponse.getStatusCode());
        assertEquals("PromoterId should be greater than 0", deleteJson.getJSONObject(0).getString("message"));
    }
}
