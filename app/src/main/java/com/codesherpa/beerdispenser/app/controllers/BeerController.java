package com.codesherpa.beerdispenser.app.controllers;

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

import com.codesherpa.beerdispenser.app.dtos.BeerDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateBeerDto;
import com.codesherpa.beerdispenser.app.models.Beer;
import com.codesherpa.beerdispenser.app.services.BeerService;
import com.codesherpa.beerdispenser.app.utils.ApiHelper;
import com.codesherpa.beerdispenser.app.exceptions.ServerException;

import java.util.List;

@RestController
@RequestMapping("/beers")
public class BeerController {

    @Autowired
    private BeerService beerService;

    @GetMapping
    public ResponseEntity<Iterable<BeerDto>> getAllBeers() {
        try {
            List<Beer> beers = beerService.getAllBeers();
            return new ResponseEntity<>(beers.stream().map(ApiHelper::toBeerDto).toList(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBeerById(@PathVariable Long id) {
        try {
            Beer beer = beerService.getBeer(id);
            if (beer != null) {
                BeerDto beerDto = ApiHelper.toBeerDto(beer);
                return new ResponseEntity<>(beerDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ServerException("Error fetching beer: " + id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addBeer(@RequestBody CreateBeerDto beerDto) {
        Beer beer = new Beer();
        beer.setName(beerDto.name);
        beer.setPricePerLitre(beerDto.pricePerLitre);
        try {
            Beer newBeer = beerService.createBeer(beer);
            return new ResponseEntity<>(ApiHelper.toBeerDto(newBeer), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ServerException("Error creating beer"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBeer(@PathVariable Long id) {
        try {
            beerService.deleteBeer(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ServerException("Error deleting beer: " + id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
