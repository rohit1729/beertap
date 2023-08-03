package com.codesherpa.beerdispenser.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codesherpa.beerdispenser.app.models.Promoter;
import com.codesherpa.beerdispenser.app.repositories.PromoterRepository;

@Service
public class PromoterService {

  @Autowired
  private PromoterRepository promoterRepository;

  public Promoter createPromoter(Promoter promoter) {
    return promoterRepository.save(promoter);
  }

  public Promoter getPromoter(Long id) {
    return promoterRepository.findById(id).orElse(null);
  }

  public List<Promoter> getAllPromoters() {
    return promoterRepository.findAll();
  }

  public void deletePromoter(Long id) {
    promoterRepository.deleteById(id);
  }
}