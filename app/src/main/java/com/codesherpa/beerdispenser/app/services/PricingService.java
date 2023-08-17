package com.codesherpa.beerdispenser.app.services;

import org.springframework.stereotype.Service;

import com.codesherpa.beerdispenser.app.dtos.request.CreateMarginDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreatePricingDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreatePricingsDto;
import com.codesherpa.beerdispenser.app.models.Admin;
import com.codesherpa.beerdispenser.app.models.Material;
import com.codesherpa.beerdispenser.app.models.Pricing;
import com.codesherpa.beerdispenser.app.models.Specification;
import com.codesherpa.beerdispenser.app.repositories.AdminRepository;
import com.codesherpa.beerdispenser.app.repositories.MaterialRepository;
import com.codesherpa.beerdispenser.app.repositories.PricingRepository;
import com.codesherpa.beerdispenser.app.repositories.SpecificationRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PricingService {

    @Autowired
    private PricingRepository pricingRepository;

    @Autowired 
    MaterialRepository materialRepository;

    @Autowired
    SpecificationRepository specificationRepository;

    public void createPricing(List<CreatePricingDto> dtos) {
        for (int i = 0; i < dtos.size(); ++i){
            CreatePricingDto pricingDto = dtos.get(i);
            List<Pricing> pricings = pricingRepository.findByMaterialIdAndSpecificationId(pricingDto.getMaterialId(), pricingDto.getSpecificationId());
            if (!pricings.isEmpty()){
                Pricing pricing = pricings.get(0);
                pricing.setPrice(pricingDto.getPrice());
                pricingRepository.save(pricing);
            }else{
                Pricing pricing = new Pricing();
                pricing.setSpecificationId(pricing.getSpecificationId());
                pricing.setMaterialId(pricingDto.materialId);
                pricing.setPrice(pricingDto.getPrice());
                pricingRepository.save(pricing);
            }
        }
    }

    public void createMargin(List<CreateMarginDto> dtos) {
        for (int i = 0; i < dtos.size(); ++i){
            CreateMarginDto marginDto = dtos.get(i);
            List<Pricing> pricings = pricingRepository.findByMaterialIdAndSpecificationId(marginDto.materialId, marginDto.specificationId);
            if (!pricings.isEmpty()){
                Pricing pricing = pricings.get(0);
                pricing.setMargin(marginDto.margin);
                pricingRepository.save(pricing);
            }else{
                Pricing pricing = new Pricing();
                pricing.setSpecificationId(marginDto.specificationId);
                pricing.setMaterialId(marginDto.materialId);
                pricing.setMargin(marginDto.margin);
                pricingRepository.save(pricing);
            }
        }
    }

    public Map<String, BigDecimal> getPrice(Long materialId, Long specificationId) {
        List<Pricing> pricings = pricingRepository.findByMaterialIdAndSpecificationId(materialId, specificationId);
        Map<String, BigDecimal> price = new HashMap<>();
        if (!pricings.isEmpty()){
            price.put("price", pricings.get(0).getPrice());
        }
        return price;
    }

    public Map<String, BigDecimal> getFinalPricing(Long materialId, Long specificationId) {
        List<Pricing> pricings = pricingRepository.findByMaterialIdAndSpecificationId(materialId, specificationId);
        Map<String, BigDecimal> response = new HashMap<>();
        if (!pricings.isEmpty()){
            Pricing pricing = pricings.get(0);
            BigDecimal margin = BigDecimal.ZERO;
            if (pricing.getMargin() != null){
                margin = pricing.getMargin();
            }
            BigDecimal price = BigDecimal.ZERO;
            if (pricing.getPrice() != null){
                price = pricing.getPrice();
            }
            BigDecimal marginAmount = margin.multiply(price).divide(BigDecimal.valueOf(100));
            BigDecimal amount = pricing.getPrice().add(marginAmount);
            response.put("price", amount);
        }
        return response;
    }

    public Map<String, BigDecimal> getMargin(Long materialId, Long specificationId) {
        List<Pricing> pricings = pricingRepository.findByMaterialIdAndSpecificationId(materialId, specificationId);
        Map<String, BigDecimal> response = new HashMap<>();
        if (!pricings.isEmpty()){
            Pricing pricing = pricings.get(0);
            response.put("margin", pricing.getMargin());
        }
        return response;
    }
    
    public void fillDummyPricing(BigDecimal price){
        List<Material> materials = materialRepository.findAll();
        for (int i = 0; i < materials.size(); ++i){
            Material material = materials.get(i);
            List<Specification> specifications = specificationRepository.findByCategoryId(material.getCategoryId());
            for (int j = 0; j < specifications.size(); ++j){
                Specification specification = specifications.get(j);
                createPricing(material.getId(), specification.getId(), price);
            }
        }
    }

    private Pricing createPricing(Long materialId, Long specificationId, BigDecimal price){
        List<Pricing> pricings = pricingRepository.findByMaterialIdAndSpecificationId(materialId, specificationId);
        if (!pricings.isEmpty()){
            Pricing pricing = pricings.get(0);
            if (pricing.getPrice() == null || (pricing.getPrice().compareTo(BigDecimal.ZERO) < 0)){
                pricing.setPrice(price);
            }
            pricingRepository.save(pricing);
            return pricing;
        }else{
            Pricing pricing = new Pricing();
            pricing.setSpecificationId(specificationId);
            pricing.setMaterialId(materialId);
            pricing.setPrice(price);
            pricingRepository.save(pricing);
            return pricing;
        }
    }
}
