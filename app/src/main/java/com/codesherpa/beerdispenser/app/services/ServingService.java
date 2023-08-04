package com.codesherpa.beerdispenser.app.services;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codesherpa.beerdispenser.app.dtos.request.CreateServingDto;
import com.codesherpa.beerdispenser.app.models.Beer;
import com.codesherpa.beerdispenser.app.models.Serving;
import com.codesherpa.beerdispenser.app.models.Tap;
import com.codesherpa.beerdispenser.app.repositories.BeerRepository;
import com.codesherpa.beerdispenser.app.repositories.ServingRepository;
import com.codesherpa.beerdispenser.app.repositories.TapRepository;

@Service
public class ServingService {

    @Autowired
    private ServingRepository servingRepository;

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private TapRepository tapRepository;
    
    public Serving createServing(Serving serving) {
        return servingRepository.save(serving);
    }
    
    public Serving getServing(Long id) {
        return servingRepository.findById(id).orElse(null);
    }
    
    public List<Serving> getAllServings() {
        return servingRepository.findAll();
    }
    
    public void deleteServing(Long id) {
        servingRepository.deleteById(id);
    }

    public Serving startServing(CreateServingDto createServingDto){
        Tap tap = tapRepository.findById(createServingDto.tapId).get();
        Beer beer = beerRepository.findById(tap.getBeerId()).get();
        Serving serving = new Serving();
        serving.setAttendeeId(createServingDto.attendeeId);
        serving.setBeerId(beer.getId());
        serving.setTapId(tap.getId());
        serving.setPromoterId(tap.getPromoterId());
        serving.setFlowPerSecond(tap.getFlowPerSecond());
        serving.setPricePerLitre(beer.getPricePerLitre());
        serving.setStartTime(Timestamp.from(Instant.now()));
        return servingRepository.save(serving);
    }

    public Serving updateEndTime(Serving serving, Timestamp endTime){
        serving.setEndTime(endTime);
        BigDecimal servingDuration = BigDecimal.valueOf(
            ChronoUnit.SECONDS.between(serving.getEndTime().toInstant(), serving.getStartTime().toInstant()));
        BigDecimal total = serving.getPricePerLitre().multiply(servingDuration).multiply(serving.getFlowPerSecond());
        serving.setTotal(total);
        return servingRepository.save(serving);
    }
}
