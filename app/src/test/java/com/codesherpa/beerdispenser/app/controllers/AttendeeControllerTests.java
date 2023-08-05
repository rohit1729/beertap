package com.codesherpa.beerdispenser.app.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.codesherpa.beerdispenser.app.dtos.request.CreateAttendeeDto;
import com.codesherpa.beerdispenser.app.models.Attendee;
import com.codesherpa.beerdispenser.app.repositories.AttendeeRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class AttendeeControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Test
    public void testCreateAttendee() throws Exception {
        CreateAttendeeDto request = new CreateAttendeeDto("");

        ResponseEntity<String> response = restTemplate.postForEntity("/attendees", request, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        JSONArray errors = new JSONArray(response.getBody());
        assertEquals("Attendee name should not be blank", errors.getJSONObject(0).getString("message"));

        request = new CreateAttendeeDto("John");
        ResponseEntity<AttendeeDto> createdResponse = restTemplate.postForEntity("/attendees", request,
                AttendeeDto.class);

        assertEquals(HttpStatus.CREATED, createdResponse.getStatusCode());
        Attendee saved = attendeeRepository.findById(createdResponse.getBody().getId()).get();
        assertEquals("John", saved.getName());
    }

    @Test
    public void testGetAttendees() throws Exception {
        // Create test attendees
        CreateAttendeeDto attendee1Request = new CreateAttendeeDto("John");
        ResponseEntity<AttendeeDto> attendee1Response = restTemplate.postForEntity("/attendees", attendee1Request,
                AttendeeDto.class);

        CreateAttendeeDto attendee2Request = new CreateAttendeeDto("Jane");
        ResponseEntity<AttendeeDto> attendee2Response = restTemplate.postForEntity("/attendees", attendee2Request,
                AttendeeDto.class);

        // Fetch all attendees
        ResponseEntity<String> response = restTemplate.getForEntity("/attendees", String.class);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());

        JSONArray attendees = new JSONArray(response.getBody());
        assertEquals(2, attendees.length());

        JSONObject attendee1Json = attendees.getJSONObject(0);
        assertEquals(attendee1Response.getBody().getId(), attendee1Json.getLong("id"));
        assertEquals(attendee1Request.getName(), attendee1Json.getString("name"));

        JSONObject attendee2Json = attendees.getJSONObject(1);
        assertEquals(attendee2Response.getBody().getId(), attendee2Json.getLong("id"));
        assertEquals(attendee2Request.getName(), attendee2Json.getString("name"));

    }

    @Test
    public void testGetAttendeeById() throws Exception {
        // Create test attendees
        CreateAttendeeDto attendee1Request = new CreateAttendeeDto("John");
        ResponseEntity<AttendeeDto> attendee1Response = restTemplate.postForEntity("/attendees", attendee1Request,
                AttendeeDto.class);
        Long attendee1Id = attendee1Response.getBody().getId();

        CreateAttendeeDto attendee2Request = new CreateAttendeeDto("Jane");
        ResponseEntity<AttendeeDto> attendee2Response = restTemplate.postForEntity("/attendees", attendee2Request,
                AttendeeDto.class);
        Long attendee2Id = attendee2Response.getBody().getId();

        // Check invalid attendee id
        ResponseEntity<String> invalidResponse = restTemplate.getForEntity("/attendees/0", String.class);
        assertEquals(HttpStatus.BAD_REQUEST, invalidResponse.getStatusCode());

        // Check non-existing attendee id
        ResponseEntity<String> notFoundResponse = restTemplate.getForEntity("/attendees/999", String.class);
        assertEquals(HttpStatus.NOT_FOUND, notFoundResponse.getStatusCode());

        // Get attendee 1
        ResponseEntity<AttendeeDto> attendee1GetResponse = restTemplate.getForEntity("/attendees/" + attendee1Id,
                AttendeeDto.class);
        assertEquals(HttpStatus.OK, attendee1GetResponse.getStatusCode());
        assertEquals(attendee1Id, attendee1GetResponse.getBody().getId());

        // Get attendee 2
        ResponseEntity<AttendeeDto> attendee2GetResponse = restTemplate.getForEntity("/attendees/" + attendee2Id,
                AttendeeDto.class);
        assertEquals(HttpStatus.OK, attendee2GetResponse.getStatusCode());
        assertEquals(attendee2Id, attendee2GetResponse.getBody().getId());
    }

    @Test
    public void testDeleteAttendee() throws Exception {

        // Create a test attendee
        CreateAttendeeDto request = new CreateAttendeeDto("John");
        ResponseEntity<AttendeeDto> createResponse = restTemplate.postForEntity("/attendees", request,
                AttendeeDto.class);

        // Delete the attendee
        Long attendeeId = createResponse.getBody().getId();
        ResponseEntity<Long> deleteResponse = restTemplate.exchange("/attendees/" + attendeeId, HttpMethod.DELETE, null,
                Long.class);

        // Verify delete succeeds
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertEquals(attendeeId, deleteResponse.getBody());

    }

    @Test
    public void testDeleteNotFoundAttendee() throws Exception {

        // Try to delete a non-existing attendee
        ResponseEntity<String> deleteResponse = restTemplate.exchange("/attendees/999", HttpMethod.DELETE, null,
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());

    }

    @Test
    public void testDeleteInvalidId() throws Exception {

        // Try to delete with invalid id
        ResponseEntity<String> deleteResponse = restTemplate.exchange("/attendees/0", HttpMethod.DELETE, null,
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, deleteResponse.getStatusCode());

    }
}
