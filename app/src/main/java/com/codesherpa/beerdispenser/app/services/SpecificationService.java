package com.codesherpa.beerdispenser.app.services;

import org.springframework.stereotype.Service;

import com.codesherpa.beerdispenser.app.models.Specification;
import com.codesherpa.beerdispenser.app.repositories.SpecificationRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SpecificationService {

    @Autowired
    private SpecificationRepository specificationRepository;

    public Specification createSpecification(Specification specification) {
        return specificationRepository.save(specification);
    }


    public List<Specification> getAllSpecification() {
        return specificationRepository.findAll();
    }

    public List<Specification> getSpecificationsByCategoryId(Long categoryId) {
        return specificationRepository.findByCategoryId(categoryId);
    }
}
