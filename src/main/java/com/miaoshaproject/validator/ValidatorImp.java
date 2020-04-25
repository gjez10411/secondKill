package com.miaoshaproject.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @Author: wenbaipei
 * @Date: 2019/6/30 20:26
 * @Version 1.0
 */
@Component
public class ValidatorImp implements InitializingBean {
    // 是真正通过javax定义的一套接口实现的一个validator工具
    private Validator validator;
    @Override
    // 当我们的SpringBean完成初始化之后会回调这个ValidatorImp的afterPropertiesSet（）方法
    public void afterPropertiesSet() throws Exception {
        // 将hibernate validator通过工厂的初始化方式使其实例化
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    // 实现校验方法并返回校验结果
    public ValidationResult validate(Object bean){
        final ValidationResult validationResult = new ValidationResult();
        // 若传入参数bean里的一些参数的规则有违背了对应validation定义的annotation的话，constrainViolationSet就会有这个值
        Set<ConstraintViolation<Object>> constrainViolationSet = validator.validate(bean);
        if(constrainViolationSet.size()>0){
            validationResult.setHasErrors(true);
            // 遍历constrainViolationSet。constrainViolation，对应到它管道执行的一个方法
            constrainViolationSet.forEach(constrainViolation->{
                // constrainViolation的errorMessage 存放了它所违背的信息。errorNessage怎么来的，之后会讲
                String errMsg = constrainViolation.getMessage();
                // 记录哪一个字段错了
                String propertyName = constrainViolation.getPropertyPath().toString();
                validationResult.getErrorMsgMap().put(propertyName,errMsg);
            });
        }
        return validationResult;
    }

}
