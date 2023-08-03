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

import com.codesherpa.beerdispenser.app.dtos.AttendeeDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateAttendeeDto;
import com.codesherpa.beerdispenser.app.exceptions.ServerException;
import com.codesherpa.beerdispenser.app.models.Attendee;
import com.codesherpa.beerdispenser.app.services.AttendeeService;
import com.codesherpa.beerdispenser.app.utils.ApiHelper;

@RestController
public class AttendeeController {

    @Autowired
    private AttendeeService attendeeService;

    @GetMapping("/attendees")
    public ResponseEntity<Object> getAllAttendees() {
        try{
            List<AttendeeDto> attendees = attendeeService.getAllAttendees()
                                                .stream().map(ApiHelper::toAttendeeDto).toList();
            return new ResponseEntity<>(attendees, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(new ServerException("Error fetching attendees"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/attendees/{id}")
    public ResponseEntity<Object> getAttendeeById(@PathVariable Long id) {
        try{
            Attendee attendee = attendeeService.getAttendee(id);
            if (attendee != null){
                AttendeeDto attendeeDto = ApiHelper.toAttendeeDto(attendee);
                return new ResponseEntity<>(attendeeDto, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new ServerException("Error fetching attendee: "+id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/attendees/{id}")
    public ResponseEntity<Object> deleteAttendeeById(@PathVariable Long id) {
        try {
            attendeeService.deleteAttendee(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ServerException("Error deleting attendee: "+id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/attendees")
    public ResponseEntity<Object> addAttendee(@RequestBody CreateAttendeeDto attendeeDto) {
        Attendee attendee = new Attendee();
        attendee.setName(attendeeDto.name);
        try{
            attendee = attendeeService.createAttendee(attendee);
            return new ResponseEntity<>(ApiHelper.toAttendeeDto(attendee), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(new ServerException(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}