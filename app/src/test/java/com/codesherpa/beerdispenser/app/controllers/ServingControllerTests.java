package com.codesherpa.beerdispenser.app.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.aspectj.lang.annotation.Before;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import com.codesherpa.beerdispenser.app.dtos.AttendeeDto;
import com.codesherpa.beerdispenser.app.dtos.BeerDto;
import com.codesherpa.beerdispenser.app.dtos.PromoterDto;
import com.codesherpa.beerdispenser.app.dtos.ServingDto;
import com.codesherpa.beerdispenser.app.dtos.TapDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateAttendeeDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateBeerDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreatePromoterDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateServingDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateTapDto;
import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;
import com.codesherpa.beerdispenser.app.models.Serving;
import com.codesherpa.beerdispenser.app.repositories.ServingRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ServingControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ServingRepository servingRepository;

    private BeerDto beer;
    private TapDto tap;
    private PromoterDto promoter;
    private AttendeeDto attendee;

    @BeforeEach
    public void setUp() {

        // Create test beer
        CreateBeerDto beerRequest = new CreateBeerDto("IPA", BigDecimal.TEN);
        ResponseEntity<BeerDto> beerResponse = restTemplate.postForEntity("/beers", beerRequest, BeerDto.class);
        beer = beerResponse.getBody();

        // Create test promoter
        CreatePromoterDto promoterRequest = new CreatePromoterDto("John", true);
        ResponseEntity<PromoterDto> promoterResponse = restTemplate.postForEntity("/promoters",
                promoterRequest, PromoterDto.class);
        promoter = promoterResponse.getBody();

        // Create a test tap
        CreateTapDto tapRequest = new CreateTapDto("Tap 1", BigDecimal.ONE, beerResponse.getBody().getId(),
                promoterResponse.getBody().getId());
        ResponseEntity<TapDto> tapResponse = restTemplate.postForEntity("/taps", tapRequest, TapDto.class);
        tap = tapResponse.getBody();

        // Create an attendee
        CreateAttendeeDto request = new CreateAttendeeDto("John");
        ResponseEntity<AttendeeDto> attendeeResponse = restTemplate.postForEntity("/attendees", request,
                AttendeeDto.class);
        attendee = attendeeResponse.getBody();
    }

    @Test
    public void testCreateServingInvalidTapId() throws Exception {

        // Test invalid tapId
        CreateServingDto invalidTapIdRequest = new CreateServingDto(0L, 1L);
        ResponseEntity<String> invalidTapIdResponse = restTemplate.postForEntity("/servings", invalidTapIdRequest,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, invalidTapIdResponse.getStatusCode());
        JSONArray invalidTapIdErrors = new JSONArray(invalidTapIdResponse.getBody());
        assertEquals(ExceptionMessage.TAP_ID_INVALID, invalidTapIdErrors.getJSONObject(0).getString("message"));
        
                // Test invalid tapId
        CreateServingDto nullTapIdRequest = new CreateServingDto(null, 1L);
        ResponseEntity<String> nullTapIdResponse = restTemplate.postForEntity("/servings", nullTapIdRequest,
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, nullTapIdResponse.getStatusCode());
        JSONArray nullTapIdErrors = new JSONArray(nullTapIdResponse.getBody());
        assertEquals(ExceptionMessage.TAP_ID_INVALID, nullTapIdErrors.getJSONObject(0).getString("message"));
    }

    @Test
    public void testCreateServingUnsetTap() throws Exception {

        // Create an unset test tap
        CreateTapDto unSetTapRequest = new CreateTapDto("Tap 1", BigDecimal.ONE, 100L, 100L);
        ResponseEntity<TapDto> unSetTapResponse = restTemplate.postForEntity("/taps", unSetTapRequest, TapDto.class);
        TapDto unSetTap = unSetTapResponse.getBody();
        // Test unset tap
        CreateServingDto unsetTapRequest = new CreateServingDto(unSetTap.getId(), attendee.getId());
        ResponseEntity<String> unsetTapResponse = restTemplate.postForEntity("/servings", unsetTapRequest,
                String.class);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unsetTapResponse.getStatusCode());
        JSONArray unsetTapErrors = new JSONArray(unsetTapResponse.getBody());
        assertEquals("Tap is not set for tapId ", unsetTapErrors.getJSONObject(0).getString("message"));
    }

    @Test
    public void testCreateServingInvalidAttendeeId() throws Exception {
        // Test invalid attendeeId
        CreateServingDto invalidAttendeeIdRequest = new CreateServingDto(1L, 0L);
        ResponseEntity<String> invalidAttendeeIdResponse = restTemplate.postForEntity("/servings",
                invalidAttendeeIdRequest, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, invalidAttendeeIdResponse.getStatusCode());
        JSONArray invalidAttendeeIdErrors = new JSONArray(invalidAttendeeIdResponse.getBody());
        assertEquals(ExceptionMessage.ATTENDEE_ID_INVALID,
                invalidAttendeeIdErrors.getJSONObject(0).getString("message"));
    }
}