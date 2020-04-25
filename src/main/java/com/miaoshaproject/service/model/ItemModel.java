package com.miaoshaproject.service.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author: wenbaipei
 * @Date: 2019/7/2 21:27
 * @Version 1.0
 */
@Data
public class ItemModel {
    private Integer id;

    // 商品名称
    @NotBlank(message = "商品名称不能为空")
    private String title;

    // 商品价格，这里用BigDecimal不用Double是因为Double在java内部传到前端存在精度的问题，比如1.9会变成1.99999999999
    @NotNull(message = "商品价格不能为空")
    @Min(value = 0,message = "商品价格必须大于0")
    private BigDecimal price;

    // 商品库存
    @NotNull(message = "库存不能不填")
    private Integer stock;

    // 商品的描述
    @NotBlank(message = "商品描述信息不能为空")
    private String description;

    // 商品的销量，非入参范围
    private Integer sales;

    // 商品的描述图片
    @NotBlank(message = "图片不能为空")
    private String imgUrl;

    // 使用聚合模型,如果promoItemModel不为空，则表示有还未结束的活动
    private PromoItemModel promoItemModel;
}
