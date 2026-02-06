package com.project.learning.stayIn.strategy;

import com.project.learning.stayIn.entity.Inventory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class HolidayPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePricing(Inventory inventory) {
        BigDecimal price = wrapped.calculatePricing(inventory);
        boolean isTodayHoliday = true;
        if(isTodayHoliday){
            price = price.multiply(BigDecimal.valueOf(1.25));
        }
        return price;
    }
}
