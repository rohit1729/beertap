package com.codesherpa.beerdispenser.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codesherpa.beerdispenser.app.dtos.BeerDto;
import com.codesherpa.beerdispenser.app.models.Beer;
import com.codesherpa.beerdispenser.app.repositories.BeerRepository;
import com.codesherpa.beerdispenser.app.utils.ApiHelper;

@Service   
public class BeerService {

    @Autowired
    private BeerRepository beerRepository;
    
    public Beer createBeer(Beer beer) {
        return beerRepository.save(beer);
    }
    
    public Beer getBeer(Long id) {
        return beerRepository.findById(id).orElse(null);
    }
    
    public List<Beer> getAllBeers() {
        return beerRepository.findAll();
    }
    
    public void deleteBeer(Long id) {
        beerRepository.deleteById(id);
    }
}
