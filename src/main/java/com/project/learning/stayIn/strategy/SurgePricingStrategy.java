package com.project.learning.stayIn.strategy;

import com.project.learning.stayIn.entity.Inventory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class SurgePricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePricing(Inventory inventory) {
        BigDecimal price = wrapped.calculatePricing(inventory);
        return price.multiply(inventory.getSurgeFactor());
    }
}
