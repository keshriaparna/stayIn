package com.project.learning.stayIn.strategy;

import com.project.learning.stayIn.entity.Inventory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class OccupanyPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePricing(Inventory inventory) {
        BigDecimal price = wrapped.calculatePricing(inventory);
        double occupanyRate = (double) inventory.getBookedCount()/inventory.getTotalCount();
        if(occupanyRate > 0.8){
            price = price.multiply(BigDecimal.valueOf(1.2));
        }
        return price;
    }
}
