package com.codesherpa.beerdispenser.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesherpa.beerdispenser.app.dtos.ServingDto;
import com.codesherpa.beerdispenser.app.dtos.TapDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateServingDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateTapDto;
import com.codesherpa.beerdispenser.app.models.Attendee;
import com.codesherpa.beerdispenser.app.models.Serving;
import com.codesherpa.beerdispenser.app.models.Tap;
import com.codesherpa.beerdispenser.app.services.AttendeeService;
import com.codesherpa.beerdispenser.app.services.ServingService;
import com.codesherpa.beerdispenser.app.services.TapService;
import com.codesherpa.beerdispenser.app.utils.ApiHelper;
import com.codesherpa.beerdispenser.app.exceptions.ServerException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/servings")
public class ServingController {

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
            return new ResponseEntity<>(new ServerException("Error creating serving"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
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
            return new ResponseEntity<>(new ServerException("Error creating serving"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
