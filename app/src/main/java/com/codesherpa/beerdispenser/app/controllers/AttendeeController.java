package com.codesherpa.beerdispenser.app.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesherpa.beerdispenser.app.dtos.AttendeeDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateAttendeeDto;
import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;
import com.codesherpa.beerdispenser.app.exceptions.ServerException;
import com.codesherpa.beerdispenser.app.models.Attendee;
import com.codesherpa.beerdispenser.app.services.AttendeeService;
import com.codesherpa.beerdispenser.app.utils.ApiHelper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/attendees")
public class AttendeeController {

    Logger logger = LoggerFactory.getLogger(AttendeeController.class);

    @Autowired
    private AttendeeService attendeeService;

    @GetMapping
    public ResponseEntity<Object> getAllAttendees() {
        try{
            List<AttendeeDto> attendees = attendeeService.getAllAttendees()
                                                .stream().map(ApiHelper::toAttendeeDto).toList();
            return new ResponseEntity<>(attendees, HttpStatus.OK);
        }catch(Exception e){
            logger.error(ExceptionMessage.ATTENDEE_LIST_500, e);
            return new ResponseEntity<>(new ServerException(ExceptionMessage.ATTENDEE_GET_500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAttendeeById(
        @PathVariable @Min(value = 1, message = ExceptionMessage.ATTENDEE_ID_INVALID) Long id) {
        try{
            Attendee attendee = attendeeService.getAttendee(id);
            if (attendee != null){
                AttendeeDto attendeeDto = ApiHelper.toAttendeeDto(attendee);
                return new ResponseEntity<>(attendeeDto, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new ServerException(ExceptionMessage.ATTENDEE_GET_500+id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAttendeeById(
        @PathVariable @Min(value = 1, message = ExceptionMessage.ATTENDEE_ID_INVALID) Long id) {
        try {
            attendeeService.deleteAttendee(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }catch (Exception e){
            logger.error(ExceptionMessage.ATTENDEE_DELETE_500, e);
            return new ResponseEntity<>(new ServerException(ExceptionMessage.ATTENDEE_DELETE_500+id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addAttendee(@Valid @RequestBody CreateAttendeeDto attendeeDto) {
        Attendee attendee = new Attendee();
        attendee.setName(attendeeDto.name);
        try{
            attendee = attendeeService.createAttendee(attendee);
            return new ResponseEntity<>(ApiHelper.toAttendeeDto(attendee), HttpStatus.OK);
        } catch(Exception e) {
            logger.error(ExceptionMessage.ATTENDEE_POST_500, e);
            return new ResponseEntity<>(new ServerException(ExceptionMessage.ATTENDEE_POST_500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}