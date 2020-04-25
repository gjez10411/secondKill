package com.miaoshaproject.controller;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wenbaipei
 * @Date: 2019/6/25 0:19
 * @Version 1.0
 */
public class BasicController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicController.class);
    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";
    // 定义exceptionhandler解决未被controller层吸收的exception
    @ExceptionHandler(Exception.class)
    // 对于BusinessException这种异常类来说，应该是业务逻辑处理上的问题，而并非服务端不能处理的错误，因此我们定义即便controller抛出了exception之后，我捕获之后还返回OK
    @ResponseStatus(HttpStatus.OK)
    // 这种方式仅仅只能返回一个页面的路径，想返回@ResponseBody这种形式，还需添加 @ResponseBody注解
    @ResponseBody
    public Object handlerException(HttpServletRequest httpServletRequest, Exception ex){
        Map<String,Object> responseData = new HashMap<>();
        if(ex instanceof BusinessException){
            BusinessException businessException = (BusinessException)ex;
            responseData.put("errCode",businessException.getErrorCode());
            responseData.put("errMsg",businessException.getErrorMsg());
            LOGGER.info(businessException.getMessage() + " : Status - {} : ErrorCodes - {}",HttpStatus.OK,businessException.getErrorCode(),businessException);
        }else {
            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrorCode());
            responseData.put("errMsg",EmBusinessError.UNKNOWN_ERROR.getErrorMsg());
            LOGGER.info(ex.getMessage() + " : Status - {}",HttpStatus.OK);
        }
        return CommonReturnType.create(responseData,"fail");
        //为什么不能直接把EmBusinessError.UNKNOWN_ERROR赋值给CommonReturnType的data变量呢，EmBusinessError.UNKNOWN_ERROR的形式也是"errCode":10001,"errMsg":"用户不存在"
        //因为@ResponseBody覆盖的默认的序列化方式会把Enum类型直接变成UNKNOWN_ERROR字符
        //return CommonReturnType.create(EmBusinessError.UNKNOWN_ERROR,"fail");
    }
}
