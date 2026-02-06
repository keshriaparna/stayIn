package com.project.learning.stayIn.strategy;

import com.project.learning.stayIn.entity.Inventory;

import java.math.BigDecimal;

public class BasePricingStrategy implements PricingStrategy{
    @Override
    public BigDecimal calculatePricing(Inventory inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
