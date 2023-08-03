package com.codesherpa.beerdispenser.app.controllers;

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

import com.codesherpa.beerdispenser.app.dtos.TapDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateTapDto;
import com.codesherpa.beerdispenser.app.models.Tap;
import com.codesherpa.beerdispenser.app.services.TapService;
import com.codesherpa.beerdispenser.app.utils.ApiHelper;
import com.codesherpa.beerdispenser.app.exceptions.ServerException;

import java.util.List;

@RestController
@RequestMapping("/taps")
public class TapController {

    @Autowired
    private TapService tapService;

    @GetMapping
    public ResponseEntity<Iterable<TapDto>> getAllTaps() {
        try {
            List<Tap> taps = tapService.getAllTaps();
            return new ResponseEntity<>(taps.stream().map(ApiHelper::toTapDto).toList(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTapById(@PathVariable Long id) {
        try {
            Tap tap = tapService.getTap(id);
            if (tap != null) {
                TapDto tapDto = ApiHelper.toTapDto(tap);
                return new ResponseEntity<>(tapDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ServerException("Error fetching tap: " + id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addTap(@RequestBody CreateTapDto createTapDto) {
        Tap tap = new Tap();
        tap.setName(createTapDto.name);
        tap.setFlowPerSecond(createTapDto.flowPerSecond);
        try {
            tap = tapService.createTap(tap);
            return new ResponseEntity<>(ApiHelper.toTapDto(tap), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ServerException("Error creating tap"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTap(@PathVariable Long id) {
        try {
            tapService.deleteTap(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ServerException("Error deleting tap: " + id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
