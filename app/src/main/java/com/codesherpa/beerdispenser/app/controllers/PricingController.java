package com.codesherpa.beerdispenser.app.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codesherpa.beerdispenser.app.dtos.request.CreateMarginDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreatePricingDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreatePricingsDto;
import com.codesherpa.beerdispenser.app.models.Pricing;
import com.codesherpa.beerdispenser.app.services.PricingService;

@RestController
@RequestMapping("/pricings")
@Validated
public class PricingController {
    Logger logger = LoggerFactory.getLogger(MaterialController.class);

    @Autowired
    private PricingService pricingService;

    @PostMapping
    public ResponseEntity<Object> createPricing(@RequestBody List<CreatePricingDto> pricingDtos) {
        pricingService.createPricing(pricingDtos);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getPricing(@RequestParam Long materialId, @RequestParam Long specificationId) {
        Map<String, BigDecimal> price = pricingService.getPrice(materialId, specificationId);
        return new ResponseEntity<>(price, HttpStatus.OK);
    }

    @GetMapping("/final")
    public ResponseEntity<Object> getFinalPricing(@RequestParam Long materialId, @RequestParam Long specificationId) {
        Map<String, BigDecimal> price = pricingService.getFinalPricing(materialId, specificationId);
        return new ResponseEntity<>(price, HttpStatus.OK);
    }

    @GetMapping("/setdummyprice")
    public ResponseEntity<Object> setDummyPrice(@RequestParam BigDecimal price) {
        pricingService.fillDummyPricing(price);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping("/margin")
    public ResponseEntity<Object> getMargin(@RequestParam Long materialId, @RequestParam Long specificationId) {
        Map<String, BigDecimal> margin = pricingService.getMargin(materialId, specificationId);
        return new ResponseEntity<>(margin, HttpStatus.OK);
    }

    @PostMapping("/margin")
    public ResponseEntity<Object> createMargin(@RequestBody List<CreateMarginDto> marginDtos) {
        pricingService.createMargin(marginDtos);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
