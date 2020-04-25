package com.miaoshaproject.error;

/**
 * @Author: wenbaipei
 * @Date: 2019/6/24 23:13
 * @Version 1.0
 */
// 包装器业务异常类实现
public class BusinessException extends Exception implements CommonError {

    // 首先它内部需要强关联一个CommonError，就是刚才的EmBusinessError类;
    private CommonError commonError;

    // 用于直接接收EmBusinessError的传参用于构造业务异常
    public BusinessException(CommonError commonError){
        super();
        this.commonError = commonError;
    }

    // 接收自定义errMsg的方式构造业务异常
    public BusinessException(CommonError commonError,String errMsg){
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);
    }
    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return this.commonError.getErrorMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
