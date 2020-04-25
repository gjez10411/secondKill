package com.miaoshaproject.controller.viewobject;

import lombok.Data;

/**
 * @Author: wenbaipei
 * @Date: 2019/6/23 23:42
 * @Version 1.0
 */
@Data
public class UserVO {
    private Integer id;
    private String name;
    private Byte gender;
    private Integer age;
    private String telphone;
}
