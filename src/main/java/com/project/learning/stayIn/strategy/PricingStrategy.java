package com.project.learning.stayIn.strategy;

import com.project.learning.stayIn.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {

    BigDecimal calculatePricing(Inventory inventory);
}
