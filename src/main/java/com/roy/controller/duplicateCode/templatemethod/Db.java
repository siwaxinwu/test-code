package com.roy.controller.duplicateCode.templatemethod;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Db {

    /**
     * 商品ID，商品价格
     */
    private static Map<Long, BigDecimal> items = new HashMap<>();

    static {
        items.put(1L, new BigDecimal("10"));
        items.put(2L, new BigDecimal("20"));
    }

    /**
     *
     * @param id  商品ID
     * @return
     */
    public static BigDecimal getItemPrice(long id) {
        return items.get(id);
    }

    public static String getUserCategory(long userId) {

        if (userId == 1L) return "Normal";
        if (userId == 2L) return "Vip";
        if (userId == 3L) return "Internal";
        return "Normal";
    }

    public static int getUserCouponPercent(long userId) {
        return 90;
    }
}
