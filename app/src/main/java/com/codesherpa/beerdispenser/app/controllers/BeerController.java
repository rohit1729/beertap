package com.codesherpa.beerdispenser.app.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesherpa.beerdispenser.app.dtos.BeerDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateBeerDto;
import com.codesherpa.beerdispenser.app.dtos.request.PatchBeerDto;
import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;
import com.codesherpa.beerdispenser.app.exceptions.ResourceNotFoundException;
import com.codesherpa.beerdispenser.app.models.Beer;
import com.codesherpa.beerdispenser.app.services.BeerService;
import com.codesherpa.beerdispenser.app.utils.ApiHelper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/beers")
@Validated
public class BeerController {
    Logger logger = LoggerFactory.getLogger(BeerController.class);

    @Autowired
    private BeerService beerService;

    @GetMapping
    public ResponseEntity<Object> getAllBeers() {
        List<Beer> beers = beerService.getAllBeers();
        return new ResponseEntity<>(beers.stream().map(ApiHelper::toBeerDto).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBeerById(
            @PathVariable @Min(value = 1, message = ExceptionMessage.BEER_ID_INVALID) Long id)
            throws Exception {
        Beer beer = beerService.getBeer(id);
        if (beer != null) {
            BeerDto beerDto = ApiHelper.toBeerDto(beer);
            return new ResponseEntity<>(beerDto, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException(ExceptionMessage.BEER_NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addBeer(@Valid @RequestBody CreateBeerDto beerDto) {
        Beer beer = new Beer();
        beer.setName(beerDto.name);
        beer.setPricePerLitre(beerDto.pricePerLitre);
        Beer newBeer = beerService.createBeer(beer);
        return new ResponseEntity<>(ApiHelper.toBeerDto(newBeer), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchBeer(@Valid @RequestBody PatchBeerDto patchBeerDto,
            @PathVariable @Min(value = 1, message = ExceptionMessage.BEER_ID_INVALID) Long id)
            throws Exception {
        Beer beer = beerService.getBeer(id);
        if (beer == null) {
            throw new ResourceNotFoundException(ExceptionMessage.BEER_NOT_FOUND);
        }
        beer = beerService.patchBeer(beer, patchBeerDto);
        return new ResponseEntity<>(ApiHelper.toBeerDto(beer), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBeer(
            @PathVariable @Min(value = 1, message = ExceptionMessage.BEER_ID_INVALID) Long id)
            throws Exception {
        Beer beer = beerService.getBeer(id);
        if (beer == null) {
            throw new ResourceNotFoundException(ExceptionMessage.BEER_NOT_FOUND);
        }
        beerService.deleteBeer(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
