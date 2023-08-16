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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesherpa.beerdispenser.app.models.Specification;
import com.codesherpa.beerdispenser.app.services.SpecificationService;
import com.codesherpa.beerdispenser.app.utils.ApiHelper;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/specifications")
@Validated
public class SpecificationController {
    Logger logger = LoggerFactory.getLogger(SpecificationController.class);

    @Autowired
    private SpecificationService specificationService;

    @GetMapping("{id}")
    public ResponseEntity<Object> getSpecifications(
        @PathVariable @Min(value = 1, message = "CategoryId invalid") Long id) {
        List<Specification> beers = specificationService.getSpecificationsByCategoryId(id);
        return new ResponseEntity<>(beers.stream().map(ApiHelper::toSpecificationDto).toList(), HttpStatus.OK);
    }
}
