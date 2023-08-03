package com.codesherpa.beerdispenser.app.utils;

import com.codesherpa.beerdispenser.app.dtos.PromoterDto;
import com.codesherpa.beerdispenser.app.dtos.ServingDto;
import com.codesherpa.beerdispenser.app.models.Promoter;
import com.codesherpa.beerdispenser.app.models.Serving;

public class ApiHelper {

    public static ServingDto toServingDto(Serving serving) {
        ServingDto dto = new ServingDto();
        dto.id = serving.getAttendeeId(); 
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

}