package com.miaoshaproject.error;

/**
 * @Author: wenbaipei
 * @Date: 2019/6/24 22:57
 * @Version 1.0
 */
public interface CommonError {
    public int getErrorCode();
    public String getErrorMsg();
    public CommonError setErrMsg(String errMsg);
}
