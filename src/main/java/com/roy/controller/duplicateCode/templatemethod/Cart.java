package com.roy.controller.duplicateCode.templatemethod;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * description：购物车
 * author：dingyawu
 * date：created in 17:10 2020/11/15
 * history:
 */
@Data
public class Cart {
    //商品清单
    private List<Item> items = new ArrayList<>();
    //总优惠
    private BigDecimal totalDiscount;
    //商品总价
    private BigDecimal totalItemPrice;
    //总运费
    private BigDecimal totalDeliveryPrice;
    //应付总价
    private BigDecimal payPrice;
}
