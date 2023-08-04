package com.codesherpa.beerdispenser.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codesherpa.beerdispenser.app.models.Tap;
import com.codesherpa.beerdispenser.app.repositories.TapRepository;

@Service
public class TapService {

    @Autowired
    private TapRepository tapRepository;
    
    public Tap createTap(Tap tap) {
        return tapRepository.save(tap);
    }
    
    public Tap getTap(Long id) {
        return tapRepository.findById(id).orElse(null);
    }
    
    public List<Tap> getAllTaps() {
        return tapRepository.findAll();
    }
    
    public void deleteTap(Long id) {
        tapRepository.deleteById(id);
    }
}
