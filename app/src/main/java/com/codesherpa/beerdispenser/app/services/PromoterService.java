package com.codesherpa.beerdispenser.app.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codesherpa.beerdispenser.app.models.Promoter;
import com.codesherpa.beerdispenser.app.models.Serving;
import com.codesherpa.beerdispenser.app.repositories.PromoterRepository;
import com.codesherpa.beerdispenser.app.repositories.ServingRepository;

@Service
public class PromoterService {

    @Autowired
    private PromoterRepository promoterRepository;

    @Autowired
    private ServingRepository servingRepository;

    public Promoter createPromoter(Promoter promoter) {
        return promoterRepository.save(promoter);
    }

    public Promoter getPromoter(Long id) {
        return promoterRepository.findById(id).orElse(null);
    }

    public List<Promoter> getAllPromoters() {
        return promoterRepository.findAll();
    }

    public void deletePromoter(Long id) {
        promoterRepository.deleteById(id);
    }

    public Map<String, Object> getPromoterEarnings(Long id) {
        Map<String, Object> earnings = new HashMap<>();
        List<Serving> servings = servingRepository.findByPromoterId(id);
        String totalKey = "total";
        earnings.put(totalKey, 0F);
        earnings.put("servings", new ArrayList<Serving>());
        servings.forEach((serving) -> {
            if (serving.getEndTime() == null) {
                // Serving is still active, so count the total till now
                Float total = serving.getFlowPerSecond()
                        * ChronoUnit.SECONDS.between(serving.getStartTime().toInstant(), Instant.now())
                        * serving.getPricePerLitre();
                earnings.put(totalKey, (total + (Float) earnings.get(totalKey)));
            } else {
                earnings.put(totalKey, ((Float) earnings.get(totalKey) + serving.getTotal()));
            }
        });
        earnings.put("servings", servings);
        return earnings;
    }

}