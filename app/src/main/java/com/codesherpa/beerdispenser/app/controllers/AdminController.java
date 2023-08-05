package com.codesherpa.beerdispenser.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesherpa.beerdispenser.app.dtos.AdminDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateAdminDto;
import com.codesherpa.beerdispenser.app.exceptions.ExceptionMessage;
import com.codesherpa.beerdispenser.app.exceptions.ResourceNotFoundException;
import com.codesherpa.beerdispenser.app.models.Admin;
import com.codesherpa.beerdispenser.app.services.AdminService;
import com.codesherpa.beerdispenser.app.utils.ApiHelper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<List<AdminDto>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return new ResponseEntity<>(admins.stream().map(ApiHelper::toAdminDto).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAdminById(@PathVariable Long id) throws Exception{
        Admin admin = adminService.getAdmin(id);
        if (admin != null) {
            AdminDto adminDto = ApiHelper.toAdminDto(admin);
            return new ResponseEntity<>(adminDto, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException(ExceptionMessage.ADMIN_NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addAdmin(@Valid @RequestBody CreateAdminDto adminDto) {
        Admin admin = new Admin();
        admin.setName(adminDto.getName());
        admin = adminService.createAdmin(admin);
        return new ResponseEntity<>(ApiHelper.toAdminDto(admin), HttpStatus.CREATED);
    }
}
