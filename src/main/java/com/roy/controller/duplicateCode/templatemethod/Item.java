package com.roy.controller.duplicateCode.templatemethod;

import lombok.Data;

import java.math.BigDecimal;

/**
 * description：购物车中的商品
 * author：dingyawu
 * date：created in 17:10 2020/11/15
 * history:
 */
@Data
public class Item {
    //商品Id
    private long id;
    //商品数量
    private int quantity;
    //商品单价
    private BigDecimal price;
    //商品优惠
    private BigDecimal couponPrice;
    //商品运费
    private BigDecimal deliveryPrice;
}
