package com.codesherpa.beerdispenser.app.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesherpa.beerdispenser.app.models.Material;
import com.codesherpa.beerdispenser.app.services.MaterialService;
import com.codesherpa.beerdispenser.app.utils.ApiHelper;

@RestController
@RequestMapping("/materials")
@Validated
public class MaterialController {
    Logger logger = LoggerFactory.getLogger(MaterialController.class);

    @Autowired
    private MaterialService materialService;

        @GetMapping
    public ResponseEntity<Object> getAllMaterial() {
        List<Material> beers = materialService.getAllMaterials();
        return new ResponseEntity<>(beers.stream().map(ApiHelper::toMaterialDto).toList(), HttpStatus.OK);
    }

}
