package com.codesherpa.beerdispenser.app.controllers;

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

import com.codesherpa.beerdispenser.app.dtos.TapDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateTapDto;
import com.codesherpa.beerdispenser.app.models.Tap;
import com.codesherpa.beerdispenser.app.services.BeerService;
import com.codesherpa.beerdispenser.app.services.PromoterService;
import com.codesherpa.beerdispenser.app.services.TapService;
import com.codesherpa.beerdispenser.app.utils.ApiHelper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;
import com.codesherpa.beerdispenser.app.exceptions.ServerException;

import java.util.List;

@RestController
@RequestMapping("/taps")
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
        try {
            List<Tap> taps = tapService.getAllTaps();
            return new ResponseEntity<>(taps.stream().map(ApiHelper::toTapDto).toList(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(ExceptionMessage.TAP_LIST_500, e);
            return new ResponseEntity<>(new ServerException(ExceptionMessage.TAP_LIST_500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTapById(
        @PathVariable @Min(value = 1, message = ExceptionMessage.TAP_ID_INVALID) Long id) {
        try {
            Tap tap = tapService.getTap(id);
            if (tap != null) {
                TapDto tapDto = ApiHelper.toTapDto(tap);
                return new ResponseEntity<>(tapDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(ExceptionMessage.TAP_GET_500, e);
            return new ResponseEntity<>(new ServerException(ExceptionMessage.TAP_GET_500 + id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addTap(@Valid @RequestBody CreateTapDto createTapDto) {
        if (beerService.getBeer(createTapDto.getBeerId()) == null){
            return new ResponseEntity<>(
                new ServerException((ExceptionMessage.BEER_NOT_FOUND)+createTapDto.getBeerId()),
                HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (promoterService.getPromoter(createTapDto.getPromoterId()) == null){
            return new ResponseEntity<>(
                new ServerException((ExceptionMessage.PROMOTER_NOT_FOUND)+createTapDto.getPromoterId()),
                HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Tap tap = createTapDto.toTap();
        try {
            tap = tapService.createTap(tap);
            return new ResponseEntity<>(ApiHelper.toTapDto(tap), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(ExceptionMessage.TAP_POST_500, e);
            return new ResponseEntity<>(new ServerException(ExceptionMessage.TAP_POST_500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTap(
        @PathVariable @Min(value = 1, message = ExceptionMessage.TAP_ID_INVALID) Long id) {
        try {
            tapService.deleteTap(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(ExceptionMessage.TAP_DELETE_500, e);
            return new ResponseEntity<>(new ServerException(ExceptionMessage.TAP_DELETE_500+id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
