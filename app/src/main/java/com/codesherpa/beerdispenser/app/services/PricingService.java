package com.codesherpa.beerdispenser.app.services;

import org.springframework.stereotype.Service;

import com.codesherpa.beerdispenser.app.dtos.request.CreatePricingDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreatePricingsDto;
import com.codesherpa.beerdispenser.app.models.Admin;
import com.codesherpa.beerdispenser.app.models.Material;
import com.codesherpa.beerdispenser.app.models.Pricing;
import com.codesherpa.beerdispenser.app.repositories.AdminRepository;
import com.codesherpa.beerdispenser.app.repositories.MaterialRepository;
import com.codesherpa.beerdispenser.app.repositories.PricingRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PricingService {

    @Autowired
    private PricingRepository pricingRepository;

    public void createPricing(CreatePricingsDto dto) {
        for (int i = 0; i < dto.getPricings().size(); ++i){
            CreatePricingDto pricingDto = dto.getPricings().get(0);
            List<Pricing> pricings = pricingRepository.findByMaterialIdAndCategoryId(dto.materialId, dto.categoryId);
            if (pricings.size() > 0){
                Pricing pricing = pricings.get(0);
                pricing.setPrice(pricingDto.price);
                pricingRepository.save(pricing);
            }else{
                Pricing pricing = new Pricing();
                pricing.setCategoryId(pricingDto.getCategoryId());
                pricing.setMaterialId(pricingDto.getMaterialId());
                pricing.setPrice(pricingDto.getPrice());
                pricingRepository.save(pricing);
            }
        }
    }


    public Pricing getPricing() {
        List<Pricing> pricings = pricingRepository.findByMaterialIdAndCategoryId(dto.materialId, dto.categoryId);
        if (!pricings.isEmpty()){
            return pricings.get(0);
        }
        return null;
    }
}
