package com.miaoshaproject.service.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: wenbaipei
 * @Date: 2019/7/4 21:53
 * @Version 1.0
 */
@Data
public class OrderModel {
    // 20181027
    String id;

    Integer userId;

    Integer itemId;

    //若非空，则表示是以秒杀商品方式下单
    Integer promoId;

    // 若promoId非空，则表示秒杀商品价格
    BigDecimal price;

    Integer amount;

    // 若promoId非空，则表示秒杀商品金额
    BigDecimal orderPrice;
}
