package com.codesherpa.beerdispenser.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
  
import com.codesherpa.beerdispenser.app.models.Serving;
import com.codesherpa.beerdispenser.app.repositories.ServingRepository;

@Service
public class ServingService {

    @Autowired
    private ServingRepository servingRepository;
    
    public Serving createServing(Serving serving) {
        return servingRepository.save(serving);
    }
    
    public Serving getServing(Long id) {
        return servingRepository.findById(id).orElse(null);
    }
    
    public Iterable<Serving> getAllServings() {
        return servingRepository.findAll();
    }
    
    public void deleteServing(Long id) {
        servingRepository.deleteById(id);
    }
}
