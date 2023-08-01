package com.codesherpa.beerdispenser.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesherpa.beerdispenser.app.models.Attendee;
import com.codesherpa.beerdispenser.app.repositories.AttendeeRepository;

@RestController
public class HelloController {

    @Autowired
    private AttendeeRepository attendeeRepository;

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

    @GetMapping("/attendees")
    public ResponseEntity<List<Attendee>> getAllCustomers() {
        List<Attendee> attendees = attendeeRepository.findAll();
        return new ResponseEntity<>(attendees, HttpStatus.OK);
    }
}