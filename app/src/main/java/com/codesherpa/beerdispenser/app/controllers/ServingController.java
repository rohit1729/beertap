package com.codesherpa.beerdispenser.app.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesherpa.beerdispenser.app.dtos.ServingDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateServingDto;
import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;
import com.codesherpa.beerdispenser.app.exceptions.InputValidationException;
import com.codesherpa.beerdispenser.app.exceptions.ResourceNotFoundException;
import com.codesherpa.beerdispenser.app.exceptions.ServerException;
import com.codesherpa.beerdispenser.app.exceptions.UnprocessableEntityException;
import com.codesherpa.beerdispenser.app.models.Attendee;
import com.codesherpa.beerdispenser.app.models.Serving;
import com.codesherpa.beerdispenser.app.models.Tap;
import com.codesherpa.beerdispenser.app.services.AttendeeService;
import com.codesherpa.beerdispenser.app.services.ServingService;
import com.codesherpa.beerdispenser.app.services.TapService;
import com.codesherpa.beerdispenser.app.utils.ApiHelper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/servings")
@Validated
public class ServingController {

    Logger logger = LoggerFactory.getLogger(ServingController.class);

    @Autowired
    private ServingService servingService;

    @Autowired
    private TapService tapService;

    @Autowired
    private AttendeeService attendeeService;

    @GetMapping
    public ResponseEntity<Object> getAllServings() {
        List<Serving> servings = servingService.getAllServings();
        return new ResponseEntity<>(servings.stream().map(ApiHelper::toServingDto).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getServingById(
            @PathVariable @Min(value = 1, message = ExceptionMessage.SERVING_ID_INVALID) Long id)
            throws Exception {
        Serving serving = servingService.getServing(id);
        if (serving != null) {
            ServingDto servingDto = ApiHelper.toServingDto(serving);
            return new ResponseEntity<>(servingDto, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException(ExceptionMessage.SERVING_NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addServing(@Valid @RequestBody CreateServingDto createServingDto) throws Exception {
        Tap tap = tapService.getTap(createServingDto.tapId);
        Attendee attendee = attendeeService.getAttendee(createServingDto.attendeeId);
        validateAddServing(createServingDto, tap, attendee);
        if (tap.getBeerId() == null || tap.getPromoterId() == null) {
            throw new UnprocessableEntityException(ExceptionMessage.TAP_NOT_SET);
        }
        return new ResponseEntity<>(ApiHelper.toServingDto(servingService.startServing(createServingDto)),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateServing(@PathVariable @Min(value = 1, message = ExceptionMessage.SERVING_ID_INVALID) Long id)
            throws Exception {
        Serving serving = servingService.getServing(id);
        if (serving == null) {
            throw new ResourceNotFoundException(ExceptionMessage.SERVING_NOT_FOUND);
        }
        if (serving.getEndTime() != null) {
            throw new UnprocessableEntityException(ExceptionMessage.SERVING_COMPLETE);
        }
        return new ResponseEntity<>(servingService.updateEndTime(serving), HttpStatus.OK);
    }


    
    // private methods to validate controllers inputs and models
    private boolean validateAddServing(CreateServingDto createServingDto, Tap tap, Attendee attendee)
            throws InputValidationException {
        InputValidationException inputValidationException = new InputValidationException();
        if (tap == null) {
            inputValidationException.exceptions.add(
                    new ServerException(ExceptionMessage.TAP_NOT_FOUND + createServingDto.tapId));
        }
        if (attendee == null) {
            inputValidationException.exceptions.add(
                    new ServerException(ExceptionMessage.ATTENDEE_NOT_FOUND + createServingDto.attendeeId));
        }
        if (!inputValidationException.exceptions.isEmpty()) {
            throw inputValidationException;
        }
        return true;
    }
}
