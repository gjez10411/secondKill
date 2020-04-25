package com.miaoshaproject.response;

import lombok.Data;

/**
 * @Author: wenbaipei
 * @Date: 2019/6/24 22:44
 * @Version 1.0
 */
@Data
public class CommonReturnType {
    // 表明对应请求的返回处理结果 success 或者 fail
    private String status;
    // 若status=success，则data返回前端需要的json数据
    // 若status=fail，则data内使用通过的错误码格式
    private Object data;

    // 定义一个通用的创建方法
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
    }

    public static CommonReturnType create(Object result,String status){
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setStatus(status);
        commonReturnType.setData(result);
        return commonReturnType;
    }
}
