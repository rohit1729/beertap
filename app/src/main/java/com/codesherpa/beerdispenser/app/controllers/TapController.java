package com.codesherpa.beerdispenser.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesherpa.beerdispenser.app.dtos.TapDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateTapDto;
import com.codesherpa.beerdispenser.app.models.Tap;
import com.codesherpa.beerdispenser.app.services.BeerService;
import com.codesherpa.beerdispenser.app.services.PromoterService;
import com.codesherpa.beerdispenser.app.services.TapService;
import com.codesherpa.beerdispenser.app.utils.ApiHelper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import com.codesherpa.beerdispenser.app.exceptions.InputValidationException;
import com.codesherpa.beerdispenser.app.exceptions.ResourceNotFoundException;
import com.codesherpa.beerdispenser.app.exceptions.InputValidationException;
import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;
import com.codesherpa.beerdispenser.app.exceptions.ServerException;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/taps")
@Validated
public class TapController {
    Logger logger = LoggerFactory.getLogger(TapController.class);

    @Autowired
    private TapService tapService;

    @Autowired
    private BeerService beerService;

    @Autowired
    private PromoterService promoterService;

    @GetMapping
    public ResponseEntity<Object> getAllTaps() {
        List<Tap> taps = tapService.getAllTaps();
        return new ResponseEntity<>(taps.stream().map(ApiHelper::toTapDto).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTapById(
        @PathVariable @Min(value = 1, message = ExceptionMessage.TAP_ID_INVALID) Long id)
        throws Exception {
        Tap tap = tapService.getTap(id);
        if (tap != null) {
            TapDto tapDto = ApiHelper.toTapDto(tap);
            return new ResponseEntity<>(tapDto, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException(ExceptionMessage.TAP_NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addTap(@Valid @RequestBody CreateTapDto createTapDto)
        throws Exception {
        validateAddTap(createTapDto);
        Tap tap = createTapDto.toTap();
        tap = tapService.createTap(tap);
        return new ResponseEntity<>(ApiHelper.toTapDto(tap), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTap(
        @PathVariable @Min(value = 1, message = ExceptionMessage.TAP_ID_INVALID) Long id) 
        throws Exception {
        if (tapService.getTap(id) == null) {
            throw new ResourceNotFoundException(ExceptionMessage.TAP_NOT_FOUND);
        }
        tapService.deleteTap(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }


    // Validation methods to validate the request params for controllers
    private boolean validateAddTap(CreateTapDto createTapDto) throws InputValidationException{
        Long beerId = createTapDto.getBeerId();
        InputValidationException inputValidationException = new InputValidationException();
        if (beerId != null && beerService.getBeer(beerId) == null){
            inputValidationException.exceptions.add(new ServerException(ExceptionMessage.BEER_NOT_FOUND));
            inputValidationException.expectedResponseCode = HttpStatus.UNPROCESSABLE_ENTITY;
            throw inputValidationException;
        }
        Long promoterId = createTapDto.getPromoterId();
        if (promoterId != null && promoterService.getPromoter(promoterId) == null){
            inputValidationException.exceptions.add(new ServerException(ExceptionMessage.PROMOTER_NOT_FOUND));
            inputValidationException.expectedResponseCode = HttpStatus.UNPROCESSABLE_ENTITY;
            throw inputValidationException;
        }
        return true;
    }

    private boolean validateDeleteTap(CreateTapDto createTapDto) throws InputValidationException{
        Long beerId = createTapDto.getBeerId();
        InputValidationException inputValidationException = new InputValidationException();
        if (beerId != null && beerService.getBeer(beerId) == null){
            inputValidationException.exceptions.add(new ServerException(ExceptionMessage.BEER_NOT_FOUND));
            inputValidationException.expectedResponseCode = HttpStatus.UNPROCESSABLE_ENTITY;
            throw inputValidationException;
        }
        Long promoterId = createTapDto.getPromoterId();
        if (promoterId != null && promoterService.getPromoter(promoterId) == null){
            inputValidationException.exceptions.add(new ServerException(ExceptionMessage.PROMOTER_NOT_FOUND));
            inputValidationException.expectedResponseCode = HttpStatus.UNPROCESSABLE_ENTITY;
            throw inputValidationException;
        }
        return true;
    }
}
