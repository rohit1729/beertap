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

import com.codesherpa.beerdispenser.app.dtos.BeerDto;
import com.codesherpa.beerdispenser.app.dtos.PromoterDto;
import com.codesherpa.beerdispenser.app.dtos.TapDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateBeerDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreatePromoterDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateTapDto;
import com.codesherpa.beerdispenser.app.models.Tap;
import com.codesherpa.beerdispenser.app.repositories.TapRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class TapControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TapRepository tapRepository;

    @Test
    public void testCreateTap() throws Exception {

        // Create a test beer
        CreateBeerDto beerRequest = new CreateBeerDto("IPA", BigDecimal.TEN);
        ResponseEntity<BeerDto> beerResponse = restTemplate.postForEntity("/beers", beerRequest, BeerDto.class);

        // Create a test promoter
        CreatePromoterDto promoterRequest = new CreatePromoterDto("John", true);
        ResponseEntity<PromoterDto> promoterResponse = restTemplate.postForEntity("/promoters", promoterRequest,
                PromoterDto.class);

        // Prepare request
        CreateTapDto tapRequest = new CreateTapDto("Tap 1", BigDecimal.ONE,
                beerResponse.getBody().getId(), promoterResponse.getBody().getId());

        // Send create request
        ResponseEntity<TapDto> response = restTemplate.postForEntity("/taps", tapRequest, TapDto.class);

        // Verify response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Tap created = tapRepository.findById(response.getBody().getId()).get();
        assertEquals(tapRequest.getName(), created.getName());
        assertEquals(tapRequest.getBeerId(), created.getBeerId());
        assertEquals(tapRequest.getPromoterId(), created.getPromoterId());
    }

    @Test
    public void testCreateWithInvalidIds() throws Exception {

        // Invalid beer id
        CreateTapDto invalidBeerIdRequest = new CreateTapDto("Tap 1", BigDecimal.ONE, 1L, 0L);
        ResponseEntity<String> invalidBeerResponse = restTemplate.postForEntity("/taps", invalidBeerIdRequest,
                String.class);
        JSONArray invalidBeerResponseJson = new JSONArray(invalidBeerResponse.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, invalidBeerResponse.getStatusCode());
        assertEquals(invalidBeerResponseJson.getJSONObject(0).getString("message"),
                "BeerId should be greater than 0");

        // Invalid promoter id
        CreateTapDto invalidPromoterIdRequest = new CreateTapDto("Tap 1", BigDecimal.ONE, 0L, 1L);
        ResponseEntity<String> invalidPromoterResponse = restTemplate.postForEntity("/taps", invalidPromoterIdRequest,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, invalidPromoterResponse.getStatusCode());
        JSONArray invalidPromoterResponseJson = new JSONArray(invalidPromoterResponse.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, invalidPromoterResponse.getStatusCode());
        assertEquals(invalidPromoterResponseJson.getJSONObject(0).getString("message"),
                "PromoterId should be greater than 0");
    }

    @Test
    public void testCreateWithUnavailableIds() throws Exception {

        // Create a beer
        CreateBeerDto beerRequest = new CreateBeerDto("IPA", BigDecimal.TEN);
        ResponseEntity<BeerDto> beerResponse = restTemplate.postForEntity("/beers", beerRequest, BeerDto.class);

        // Create a promoter
        CreatePromoterDto promoterRequest = new CreatePromoterDto("John", true);
        ResponseEntity<PromoterDto> promoterResponse = restTemplate.postForEntity("/promoters", promoterRequest,
                PromoterDto.class);

        // Use non-existing beer id
        CreateTapDto invalidBeerIdRequest = new CreateTapDto("Tap 1", BigDecimal.ONE,
                promoterResponse.getBody().getId(), 999L);

        ResponseEntity<String> invalidBeerResponse = restTemplate.postForEntity("/taps", invalidBeerIdRequest,
                String.class);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, invalidBeerResponse.getStatusCode());

        // Use non-existing promoter id
        CreateTapDto invalidPromoterIdRequest = new CreateTapDto("Tap 1", BigDecimal.ONE, 999L,
                beerResponse.getBody().getId());

        ResponseEntity<String> invalidPromoterResponse = restTemplate.postForEntity("/taps", invalidPromoterIdRequest,
                String.class);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, invalidPromoterResponse.getStatusCode());

    }

    @Test
    public void testGetTaps() throws Exception {

        // Create a test beer
        CreateBeerDto beerRequest = new CreateBeerDto("IPA", BigDecimal.TEN);
        ResponseEntity<BeerDto> beerResponse = restTemplate.postForEntity("/beers", beerRequest, BeerDto.class);

        // Create a test promoter
        CreatePromoterDto promoterRequest = new CreatePromoterDto("John", true);
        ResponseEntity<PromoterDto> promoterResponse = restTemplate.postForEntity("/promoters", promoterRequest,
                PromoterDto.class);

        // Create some test taps
        CreateTapDto tap1Request = new CreateTapDto("IPA", BigDecimal.TEN,
                beerResponse.getBody().getId(), promoterResponse.getBody().getId());
        ResponseEntity<TapDto> tap1Response = restTemplate.postForEntity("/taps", tap1Request, TapDto.class);

        CreateTapDto tap2Request = new CreateTapDto("Stout", BigDecimal.TEN,
                beerResponse.getBody().getId(), promoterResponse.getBody().getId());
        ResponseEntity<TapDto> tap2Response = restTemplate.postForEntity("/taps", tap2Request, TapDto.class);

        // Fetch all taps
        ResponseEntity<String> response = restTemplate.getForEntity("/taps", String.class);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());

        JSONArray taps = new JSONArray(response.getBody());
        assertEquals(2, taps.length());

        JSONObject tap1Json = taps.getJSONObject(0);
        assertEquals(tap1Response.getBody().getId(), tap1Json.getLong("id"));
        assertEquals(tap1Request.getName(), tap1Json.getString("name"));

        JSONObject tap2Json = taps.getJSONObject(1);
        assertEquals(tap2Response.getBody().getId(), tap2Json.getLong("id"));
        assertEquals(tap2Request.getName(), tap2Json.getString("name"));

    }

    @Test
    public void testGetTapById() throws Exception {

        // Create IPA test beer
        CreateBeerDto beerRequest = new CreateBeerDto("IPA", BigDecimal.TEN);
        ResponseEntity<BeerDto> beerResponse = restTemplate.postForEntity("/beers", beerRequest, BeerDto.class);

        // Create stout test beer
        CreateBeerDto stoutBeerRequest = new CreateBeerDto("Stout", BigDecimal.TEN);
        ResponseEntity<BeerDto> stoutBeerResponse = restTemplate.postForEntity("/beers", beerRequest, BeerDto.class);

        // Create a test promoter
        CreatePromoterDto promoterRequest = new CreatePromoterDto("John", true);
        ResponseEntity<PromoterDto> promoterResponse = restTemplate.postForEntity("/promoters", promoterRequest,
                PromoterDto.class);
        // Create test taps
        CreateTapDto tap1Request = new CreateTapDto("IPA Tap", BigDecimal.TEN,
                promoterResponse.getBody().getId(), beerResponse.getBody().getId());
        ResponseEntity<TapDto> tap1Response = restTemplate.postForEntity("/taps", tap1Request, TapDto.class);
        Long tap1Id = tap1Response.getBody().getId();

        CreateTapDto tap2Request = new CreateTapDto("Stout Tap", BigDecimal.TEN,
                promoterResponse.getBody().getId(), stoutBeerResponse.getBody().getId());

        ResponseEntity<TapDto> tap2Response = restTemplate.postForEntity("/taps", tap2Request, TapDto.class);
        Long tap2Id = tap2Response.getBody().getId();

        // Check invalid tap id
        ResponseEntity<String> invalidResponse = restTemplate.getForEntity("/taps/0", String.class);
        assertEquals(HttpStatus.BAD_REQUEST, invalidResponse.getStatusCode());

        // Check non-existing tap id
        ResponseEntity<String> notFoundResponse = restTemplate.getForEntity("/taps/999", String.class);
        assertEquals(HttpStatus.NOT_FOUND, notFoundResponse.getStatusCode());

        // Get tap 1
        ResponseEntity<TapDto> tap1GetResponse = restTemplate.getForEntity("/taps/" + tap1Id, TapDto.class);
        assertEquals(HttpStatus.OK, tap1GetResponse.getStatusCode());
        assertEquals(tap1Id, tap1GetResponse.getBody().getId());

        // Get tap 2
        ResponseEntity<TapDto> tap2GetResponse = restTemplate.getForEntity("/taps/" + tap2Id, TapDto.class);
        assertEquals(HttpStatus.OK, tap2GetResponse.getStatusCode());
        assertEquals(tap2Id, tap2GetResponse.getBody().getId());

    }

    @Test
    public void testDeleteTap() throws Exception {

        // Create a test beer
        CreateBeerDto beerRequest = new CreateBeerDto("IPA", BigDecimal.TEN);
        ResponseEntity<BeerDto> beerResponse = restTemplate.postForEntity("/beers", beerRequest, BeerDto.class);

        // Create a test promoter
        CreatePromoterDto promoterRequest = new CreatePromoterDto("John", true);
        ResponseEntity<PromoterDto> promoterResponse = restTemplate.postForEntity("/promoters", promoterRequest,
                PromoterDto.class);

        // Create a test tap
        CreateTapDto tapRequest = new CreateTapDto("Tap 1", BigDecimal.ONE, beerResponse.getBody().getId(),
                promoterResponse.getBody().getId());
        ResponseEntity<TapDto> tapResponse = restTemplate.postForEntity("/taps", tapRequest, TapDto.class);

        // Delete the tap
        Long tapId = tapResponse.getBody().getId();
        ResponseEntity<String> deleteResponse = restTemplate.exchange("/taps/" + tapId, HttpMethod.DELETE, null,
                String.class);

        // Verify delete succeeds
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertEquals(tapId.toString(), deleteResponse.getBody());

        // Fetch deleted tap
        ResponseEntity<String> getResponse = restTemplate.getForEntity("/taps/" + tapId, String.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }
}
