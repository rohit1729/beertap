package com.codesherpa.beerdispenser.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codesherpa.beerdispenser.app.dtos.request.PatchTapDto;
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

    public Tap patchTap(Tap tap, PatchTapDto patchTapDto){
        if (patchTapDto.flowPerSecond != null) tap.setFlowPerSecond(patchTapDto.flowPerSecond);
        if (patchTapDto.getPromoterId() != null) tap.setPromoterId(patchTapDto.getPromoterId());
        if (patchTapDto.getBeerId() != null) tap.setBeerId(patchTapDto.getBeerId());
        if (patchTapDto.getName() != null) tap.setName(patchTapDto.getName());
        return tapRepository.save(tap);
    }
}
