package com.roy.controller.duplicateCode.templatemethod.right;

import com.roy.controller.duplicateCode.templatemethod.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service(value = "InternalUserCart")
public class InternalUserCart extends AbstractCart {
    @Override
    protected void processCouponPrice(long userId, Item item) {
        item.setCouponPrice(BigDecimal.ZERO);
    }

    @Override
    protected void processDeliveryPrice(long userId, Item item) {
        item.setDeliveryPrice(BigDecimal.ZERO);
    }
}
