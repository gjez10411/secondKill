package com.miaoshaproject.controller.viewobject;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * @Author: wenbaipei
 * @Date: 2019/7/2 22:34
 * @Version 1.0
 */
@Data
public class ItemVO {
    private Integer id;

    // 商品名称
    private String title;

    // 商品价格，这里用BigDecimal不用Double是因为Double在java内部传到前端存在精度的问题，比如1.9会变成1.99999999999
    private BigDecimal price;

    // 商品库存
    private Integer stock;

    // 商品的描述
    private String description;

    // 商品的销量，非入参范围
    private Integer sales;

    // 商品的描述图片
    private String imgUrl;

    // 记录商品是否在秒杀活动中,0 表示没有秒杀活动 1表示秒杀活动待开始，2表示秒杀活动正在进行中
    private Integer promoStatus;

    // 秒杀活动价格
    private BigDecimal promoPrice;

    // 秒杀活动Id
    private Integer promoId;

    // 秒杀活动的开始时间
    private String startDate;
}
