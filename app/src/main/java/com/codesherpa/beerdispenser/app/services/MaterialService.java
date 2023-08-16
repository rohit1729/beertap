package com.codesherpa.beerdispenser.app.services;

import org.springframework.stereotype.Service;

import com.codesherpa.beerdispenser.app.models.Admin;
import com.codesherpa.beerdispenser.app.models.Material;
import com.codesherpa.beerdispenser.app.repositories.AdminRepository;
import com.codesherpa.beerdispenser.app.repositories.MaterialRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    public Material createMaterial(Material material) {
        return materialRepository.save(material);
    }


    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }
}
