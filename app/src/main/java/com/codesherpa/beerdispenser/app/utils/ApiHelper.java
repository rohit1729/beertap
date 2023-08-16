package com.codesherpa.beerdispenser.app.utils;

import com.codesherpa.beerdispenser.app.dtos.AdminDto;
import com.codesherpa.beerdispenser.app.dtos.AttendeeDto;
import com.codesherpa.beerdispenser.app.dtos.BeerDto;
import com.codesherpa.beerdispenser.app.dtos.MaterialDto;
import com.codesherpa.beerdispenser.app.dtos.PromoterDto;
import com.codesherpa.beerdispenser.app.dtos.ServingDto;
import com.codesherpa.beerdispenser.app.dtos.SpecificationDto;
import com.codesherpa.beerdispenser.app.dtos.TapDto;
import com.codesherpa.beerdispenser.app.models.Admin;
import com.codesherpa.beerdispenser.app.models.Attendee;
import com.codesherpa.beerdispenser.app.models.Beer;
import com.codesherpa.beerdispenser.app.models.Material;
import com.codesherpa.beerdispenser.app.models.Promoter;
import com.codesherpa.beerdispenser.app.models.Serving;
import com.codesherpa.beerdispenser.app.models.Specification;
import com.codesherpa.beerdispenser.app.models.Tap;

public class ApiHelper {

    public static ServingDto toServingDto(Serving serving) {
        ServingDto dto = new ServingDto();
        dto.id = serving.getId();
        dto.startTime = serving.getStartTime();
        dto.endTime = serving.getEndTime();
        dto.beerId = serving.getBeerId();
        dto.tapId = serving.getTapId();
        dto.promoterId = serving.getPromoterId();
        dto.attendeeId = serving.getAttendeeId();
        dto.flowPerSecond = serving.getFlowPerSecond();
        dto.pricePerLitre = serving.getPricePerLitre();
        dto.total = serving.getTotal();
        return dto;
    }

    public static PromoterDto toPromoterDto(Promoter promoter) {
        PromoterDto dto = new PromoterDto();
        dto.setId(promoter.getId());
        dto.setName(promoter.getName());
        dto.setActive(promoter.isActive());
        return dto;
    }

    public static TapDto toTapDto(Tap tap) {
        TapDto dto = new TapDto();
        dto.setId(tap.getId());
        dto.setName(tap.getName());
        dto.setPromoterId(tap.getPromoterId());
        dto.setBeerId(tap.getBeerId());
        dto.setFlowPerSecond(tap.getFlowPerSecond());
        return dto;
    }

    public static AdminDto toAdminDto(Admin admin) {
        AdminDto dto = new AdminDto();
        dto.setId(admin.getId());
        dto.setName(admin.getName());
        return dto;
    }

    public static BeerDto toBeerDto(Beer beer) {
        BeerDto dto = new BeerDto();
        dto.setId(beer.getId());
        dto.setName(beer.getName());
        dto.setPricePerLitre(beer.getPricePerLitre());
        return dto;
    }

    public static AttendeeDto toAttendeeDto(Attendee attendee) {
        AttendeeDto dto = new AttendeeDto();
        dto.setId(attendee.getId());
        dto.setName(attendee.getName());
        return dto;
    }

    public static MaterialDto toMaterialDto(Material material) {
        MaterialDto dto = new MaterialDto();
        dto.setId(material.getId());
        dto.setName(material.getName());
        dto.setPrice(material.getPrice());
        dto.setMargin(material.getMargin());
        dto.setCategoryId(material.getCategoryId());
        return dto;
    }

    public static SpecificationDto toSpecificationDto(Specification specification) {
        SpecificationDto dto = new SpecificationDto();
        dto.setId(specification.getId());
        dto.setName(specification.getName());
        dto.setCategoryId(specification.getCategoryId());
        dto.setUnit(specification.getUnit());
        return dto;
    }
}