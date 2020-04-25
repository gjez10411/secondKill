package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.PromoDOMapper;
import com.miaoshaproject.dataobject.PromoDO;
import com.miaoshaproject.service.PromoService;
import com.miaoshaproject.service.model.PromoItemModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: wenbaipei
 * @Date: 2019/7/14 20:13
 * @Version 1.0
 */
@Service
public class PeomoServiceImpl implements PromoService {
    @Autowired
    private PromoDOMapper promoDOMapper;
    @Override
    public PromoItemModel getPromoByItemId(Integer itemId) {
        // 获取对应商品的秒杀活动信息
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);

        // dataObject转化为Model
        PromoItemModel promoItemModel = convertFromDataObject(promoDO);
        if(promoItemModel==null){
            return null;
        }
        // 判断当前活动是否即将开始还是正在进行
        if(promoItemModel.getStartDate().isAfterNow()){
            promoItemModel.setStatus(1);
        }else if(promoItemModel.getEndDate().isBeforeNow()){
            promoItemModel.setStatus(3);
        }else {
            promoItemModel.setStatus(2);
        }
        return promoItemModel;
    }

    private PromoItemModel convertFromDataObject(PromoDO promoDO){
        if(promoDO==null){
            return null;
        }
        PromoItemModel promoItemModel = new PromoItemModel();
        BeanUtils.copyProperties(promoDO,promoItemModel);
        promoItemModel.setStartDate(new DateTime(promoDO.getStartData()));
        promoItemModel.setEndDate(new DateTime(promoDO.getEndData()));
        return promoItemModel;
    }
}
