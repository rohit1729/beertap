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

import com.codesherpa.beerdispenser.app.dtos.PromoterDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreatePromoterDto;
import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;
import com.codesherpa.beerdispenser.app.exceptions.ServerException;
import com.codesherpa.beerdispenser.app.models.Promoter;
import com.codesherpa.beerdispenser.app.services.PromoterService;
import com.codesherpa.beerdispenser.app.utils.ApiHelper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/promoters")
public class PromoterController {

    Logger logger = LoggerFactory.getLogger(PromoterController.class);

    @Autowired
    private PromoterService promoterService;

    @GetMapping
    public ResponseEntity<Object> getAllPromoters() {
        try {
            List<Promoter> promoters = promoterService.getAllPromoters();
            return new ResponseEntity<>(promoters.stream().map(ApiHelper::toPromoterDto).toList(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(ExceptionMessage.PROMOTER_LIST_500, e);
            return new ResponseEntity<>(new ServerException(ExceptionMessage.PROMOTER_LIST_500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPromoterById(
        @PathVariable @Min(value = 1, message = ExceptionMessage.PROMOTER_ID_INVALID) Long id) {
        try {
            Promoter promoter = promoterService.getPromoter(id);
            if (promoter != null) {
                PromoterDto promoterDto = ApiHelper.toPromoterDto(promoter);
                return new ResponseEntity<>(promoterDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(ExceptionMessage.PROMOTER_GET_500, e);
            return new ResponseEntity<>(new ServerException(ExceptionMessage.PROMOTER_GET_500+id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addPromoter(
        @RequestBody @Valid CreatePromoterDto promoterDto) {
        Promoter promoter = new Promoter();
        promoter.setName(promoterDto.getName());
        promoter.setActive(promoterDto.isActive());
        try {
            promoter = promoterService.createPromoter(promoter);
            return new ResponseEntity<>(ApiHelper.toPromoterDto(promoter), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(ExceptionMessage.PROMOTER_POST_500, e);
            return new ResponseEntity<>(new ServerException(ExceptionMessage.PROMOTER_POST_500),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePromoter(
        @PathVariable @Min(value = 1, message = ExceptionMessage.PROMOTER_ID_INVALID) Long id) {
        try {
            promoterService.deletePromoter(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(ExceptionMessage.PROMOTER_DELETE_500, e);
            return new ResponseEntity<>(new ServerException(ExceptionMessage.PROMOTER_DELETE_500),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/earnings")
    public ResponseEntity<Object> getEarnings(
        @PathVariable @Min(value = 1, message = ExceptionMessage.PROMOTER_ID_INVALID) Long id) {
        try {
            return new ResponseEntity<>(promoterService.getPromoterEarnings(id), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(ExceptionMessage.PROMOTER_EARNINGS_500, e);
            return new ResponseEntity<>(new ServerException(ExceptionMessage.PROMOTER_EARNINGS_500 + id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
