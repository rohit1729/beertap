package com.codesherpa.beerdispenser.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codesherpa.beerdispenser.app.dtos.CreateAttendeeDto;
import com.codesherpa.beerdispenser.app.exceptions.ServerException;
import com.codesherpa.beerdispenser.app.models.Attendee;
import com.codesherpa.beerdispenser.app.repositories.AttendeeRepository;

@RestController
public class AttendeeController {

    @Autowired
    private AttendeeRepository attendeeRepository;

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

    @GetMapping("/attendees")
    public ResponseEntity<Object> getAllAttendees() {
        try{
            List<Attendee> attendees = attendeeRepository.findAll();
            return new ResponseEntity<>(attendees, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(new ServerException("Error fetching attendees"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/attendees/{id}")
    public ResponseEntity<Attendee> getAttendeeById(@PathVariable Long id) {
        Attendee attendee = attendeeRepository.findById(id).get();
        HttpStatus status = attendee != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<Attendee>(attendee, status);
    }

    @DeleteMapping("/attendees/{id}")
    public ResponseEntity<Long> deleteAttendeeById(@PathVariable Long id) {
        attendeeRepository.deleteById(id);
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    @PostMapping("/attendees")
    public ResponseEntity<Object> addAttendee(@RequestBody CreateAttendeeDto attendeeDto) {
        Attendee attendee = new Attendee();
        attendee.setName(attendeeDto.name);
        try{
            attendee = attendeeRepository.save(attendee);
            return new ResponseEntity<>(attendee, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(new ServerException("Error creating attendee"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}