package com.codesherpa.beerdispenser.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesherpa.beerdispenser.app.dtos.PromoterDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreatePromoterDto;
import com.codesherpa.beerdispenser.app.exceptions.ServerException;
import com.codesherpa.beerdispenser.app.models.Promoter;
import com.codesherpa.beerdispenser.app.services.PromoterService;
import com.codesherpa.beerdispenser.app.utils.ApiHelper;

@RestController
@RequestMapping("/promoters")
public class PromoterController {

    @Autowired
    private PromoterService promoterService;

    @GetMapping
    public ResponseEntity<List<PromoterDto>> getAllPromoters() {
        try {
            List<Promoter> promoters = promoterService.getAllPromoters();
            return new ResponseEntity<>(promoters.stream().map(ApiHelper::toPromoterDto).toList(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPromoterById(@PathVariable Long id) {
        try {
            Promoter promoter = promoterService.getPromoter(id);
            if (promoter != null) {
                PromoterDto promoterDto = ApiHelper.toPromoterDto(promoter);
                return new ResponseEntity<>(promoterDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ServerException("Error fetching promoter: " + id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addPromoter(@RequestBody CreatePromoterDto promoterDto) {
        Promoter promoter = new Promoter();
        promoter.setName(promoterDto.getName());
        promoter.setActive(promoterDto.isActive());

        try {
            promoter = promoterService.createPromoter(promoter);
            return new ResponseEntity<>(ApiHelper.toPromoterDto(promoter), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ServerException("Error creating promoter"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePromoter(@PathVariable Long id) {
        try {
            promoterService.deletePromoter(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ServerException("Error deleting promoter: " + id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
