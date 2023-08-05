package com.codesherpa.beerdispenser.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codesherpa.beerdispenser.app.dtos.request.PatchBeerDto;
import com.codesherpa.beerdispenser.app.models.Beer;
import com.codesherpa.beerdispenser.app.repositories.BeerRepository;

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

    public Beer patchBeer(Beer beer, PatchBeerDto patchBeerDto){
        if (patchBeerDto.getName() != null) beer.setName(patchBeerDto.getName());
        if (patchBeerDto.getPricePerLitre() != null) beer.getPricePerLitre();
        return beerRepository.save(beer);
    }
}
