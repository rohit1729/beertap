package com.codesherpa.beerdispenser.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesherpa.beerdispenser.app.dtos.ServingDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateServingDto;
import com.codesherpa.beerdispenser.app.dtos.request.UpdateServingDto;
import com.codesherpa.beerdispenser.app.models.Attendee;
import com.codesherpa.beerdispenser.app.models.Serving;
import com.codesherpa.beerdispenser.app.models.Tap;
import com.codesherpa.beerdispenser.app.services.AttendeeService;
import com.codesherpa.beerdispenser.app.services.ServingService;
import com.codesherpa.beerdispenser.app.services.TapService;
import com.codesherpa.beerdispenser.app.utils.ApiHelper;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;
import com.codesherpa.beerdispenser.app.exceptions.ServerException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/servings")
public class ServingController {

    Logger logger = LoggerFactory.getLogger(ServingController.class);

    @Autowired
    private ServingService servingService;

    @Autowired
    private TapService tapService;

    @Autowired
    private AttendeeService attendeeService;

    @GetMapping
    public ResponseEntity<List<ServingDto>> getAllServings() {
        try {
            List<Serving> servings = servingService.getAllServings();
            return new ResponseEntity<>(servings.stream().map(ApiHelper::toServingDto).toList(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getServingById(@PathVariable Long id) {
        try {
            Serving serving = servingService.getServing(id);
            if (serving != null) {
                ServingDto servingDto = ApiHelper.toServingDto(serving);
                return new ResponseEntity<>(servingDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ServerException("Error fetching serving: " + id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addServing(@RequestBody CreateServingDto createServingDto) {
        try {
            Tap tap = tapService.getTap(createServingDto.tapId);
            Attendee attendee = attendeeService.getAttendee(createServingDto.attendeeId);

            if (tap != null && attendee != null){
                if (tap.getBeerId() == null || tap.getPromoterId() == null){
                    return new ResponseEntity<>("Tap with id: "+createServingDto.tapId+" not set", HttpStatus.UNPROCESSABLE_ENTITY);
                }
                return new ResponseEntity<>(ApiHelper.toServingDto(servingService.startServing(createServingDto)), HttpStatus.OK);
            }else{
                List<ServerException> exceptions = new ArrayList<>();
                if (tap == null){
                    exceptions.add(new ServerException("Tap with id: "+createServingDto.tapId+" not found"));
                }
                if (attendee == null){
                    exceptions.add(new ServerException("Attendee with id: "+createServingDto.attendeeId+ "not found"));
                }
                return new ResponseEntity<>(exceptions, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            logger.error(ExceptionMessage.SERVING_POST_500, e);
            return new ResponseEntity<>(new ServerException(ExceptionMessage.SERVING_POST_500),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateServing(@RequestBody UpdateServingDto updateServingDto, @PathVariable Long id) {
        try {
            if (updateServingDto.endTime == null){
                return new ResponseEntity<>(new ServerException("Serving has invalid end time"),
                    HttpStatus.BAD_REQUEST);
            }

            Serving serving = servingService.getServing(id);
            if (serving == null){
                return new ResponseEntity<>(new ServerException("Serving with id: "+id+ " not found"),
                    HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(servingService.updateEndTime(serving, updateServingDto.endTime), HttpStatus.OK);
        }catch (Exception e){
            logger.error(ExceptionMessage.SERVING_PUT_500, e);
            return new ResponseEntity<>(new ServerException(ExceptionMessage.SERVING_PUT_500),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
