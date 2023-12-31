package com.codesherpa.beerdispenser.app.controllers;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesherpa.beerdispenser.app.dtos.PromoterDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreatePromoterDto;
import com.codesherpa.beerdispenser.app.dtos.request.UpdatePromoterDto;
import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;
import com.codesherpa.beerdispenser.app.exceptions.ResourceNotFoundException;
import com.codesherpa.beerdispenser.app.exceptions.ServerException;
import com.codesherpa.beerdispenser.app.models.Promoter;
import com.codesherpa.beerdispenser.app.services.PromoterService;
import com.codesherpa.beerdispenser.app.utils.ApiHelper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Controller to manage beer promoters.
 */
@RestController
@RequestMapping("/promoters")
@Validated
public class PromoterController {

    Logger logger = LoggerFactory.getLogger(PromoterController.class);

    @Autowired
    private PromoterService promoterService;

    /**
     * Get all promoters.
     * 
     * @return List of PromoterDto
     */
    @GetMapping
    public ResponseEntity<Object> getAllPromoters() {
        List<Promoter> promoters = promoterService.getAllPromoters();
        return new ResponseEntity<>(promoters.stream().map(ApiHelper::toPromoterDto).toList(), HttpStatus.OK);
    }

    /**
     * Get a promoter by id.
     * 
     * @param id Promoter id
     * @return PromoterDto
     * @throws ResourceNotFoundException If no promoter found for id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPromoterById(
            @PathVariable @Min(value = 1, message = ExceptionMessage.PROMOTER_ID_INVALID) Long id)
            throws Exception {
        Promoter promoter = promoterService.getPromoter(id);
        if (promoter != null) {
            PromoterDto promoterDto = ApiHelper.toPromoterDto(promoter);
            return new ResponseEntity<>(promoterDto, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException(ExceptionMessage.PROMOTER_NOT_FOUND);
        }
    }

    /**
     * Create a new promoter.
     * 
     * @param createPromoterDto Promoter data transfer object
     * @return Created PromoterDto
     */
    @PostMapping
    public ResponseEntity<Object> addPromoter(
            @RequestBody @Valid CreatePromoterDto promoterDto) {
        Promoter promoter = new Promoter();
        promoter.setName(promoterDto.getName());
        promoter.setActive(promoterDto.isActive());
        promoter = promoterService.createPromoter(promoter);
        return new ResponseEntity<>(ApiHelper.toPromoterDto(promoter), HttpStatus.CREATED);
    }

    /**
     * Update an existing promoter.
     * 
     * @param id                Promoter id to update
     * @param updatePromoterDto Promoter data transfer object with updated data
     * @return Updated PromoterDto
     * @throws ResourceNotFoundException If no promoter found for id
     */
    @PutMapping("/{id}")
    public ResponseEntity<PromoterDto> updatePromoter(@PathVariable Long id,
            @RequestBody UpdatePromoterDto updatePromoterDto) throws Exception {
        Promoter existingPromoter = promoterService.getPromoter(id);
        if (existingPromoter == null) {
            throw new ResourceNotFoundException(ExceptionMessage.PROMOTER_NOT_FOUND);
        }
        existingPromoter.setName(updatePromoterDto.getName());
        promoterService.updatePromoter(existingPromoter);
        return new ResponseEntity<>(ApiHelper.toPromoterDto(existingPromoter), HttpStatus.OK);
    }

    /**
     * Delete a promoter.
     * 
     * @param id Promoter id
     * @return Deleted promoter id
     * @throws ResourceNotFoundException If no promoter found for id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePromoter(
            @PathVariable @Min(value = 1, message = ExceptionMessage.PROMOTER_ID_INVALID) Long id)
            throws Exception {
        Promoter promoter = promoterService.getPromoter(id);
        if (promoter == null) {
            throw new ResourceNotFoundException(ExceptionMessage.PROMOTER_NOT_FOUND);
        }
        promoterService.deletePromoter(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/{id}/earnings")
    public ResponseEntity<Object> getEarnings(
            @PathVariable @Min(value = 1, message = ExceptionMessage.PROMOTER_ID_INVALID) Long id) {
        return new ResponseEntity<>(promoterService.getPromoterEarnings(id), HttpStatus.OK);
    }
}
