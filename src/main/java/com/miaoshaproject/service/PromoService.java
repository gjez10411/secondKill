package com.miaoshaproject.service;

import com.miaoshaproject.service.model.PromoItemModel;

/**
 * @Author: wenbaipei
 * @Date: 2019/7/14 20:11
 * @Version 1.0
 */
public interface PromoService {
    PromoItemModel getPromoByItemId(Integer itemId);
}
