package com.miaoshaproject.service.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * @Author: wenbaipei
 * @Date: 2019/7/13 22:51
 * @Version 1.0
 */
@Data
public class PromoItemModel {
    private Integer id;
    //秒杀活动状态 1表示还未开始 2正在进行中 3表示结束
    private Integer status;
    private String promoName;
    private DateTime startDate;
    private DateTime endDate;
    private Integer itemId;
    private BigDecimal promoItemPrice;
}
