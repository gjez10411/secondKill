package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dao.UserPasswordDOMapper;
import com.miaoshaproject.dataobject.UserDO;
import com.miaoshaproject.dataobject.UserPasswordDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import com.miaoshaproject.validator.ValidationResult;
import com.miaoshaproject.validator.ValidatorImp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: wenbaipei
 * @Date: 2019/6/23 23:11
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;
    @Autowired
    private ValidatorImp validatorImp;
    @Override
    public UserModel getUserById(Integer id) {
        // 调用userdomapper获取到对应的用户dataobject
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if(userDO==null){
            return null;
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(id);
        // SpringMVC其中的一个Model定义分为三层，第一层：datapbject 和数据库完完全全一一映射
        // 第二层 领域模型
        return convertFromDataObject(userDO,userPasswordDO);
    }

    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){
        if(userDO ==null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        if(userPasswordDO!=null){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return userModel;
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        /*if(StringUtils.isEmpty(userModel.getName())
            || userModel.getGender() == null
            || userModel.getAge() == null
            || StringUtils.isEmpty(userModel.getTelphone())){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }*/
        ValidationResult validationResult = validatorImp.validate(userModel);
        if(validationResult.isHasErrors()){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,validationResult.getErrMag());
        }
        // model -> dataobject
        UserDO userDO = convertFromModel(userModel);
        // 为什么选择insertSelective，而不选择insert?
        // insertSelective会先判断databoject里边对应的字段是否为null，不为null，执行insert操作；如果为null，就不insert这个字段。
        // 不insert databoject为null的字段有什么好处？
        // 完全依赖数据库列的默认值，不会用null覆盖掉数据库的默认值。databoject为null的意思是未定义，不覆盖。这种操作在update操作里边尤其有用。
        // 因为如果这个字段是为null的话，那我就认定它是不更新这个字段。
        // 在数据库的设计当中尽量避免使用null字段的好处。
        // 在设计userinfo表的时候，把所有的字段都勾上了not null，并且设置空字符串作为它的默认值。
        // 在java处理空指针的时候，本身就很脆弱。
        // 而且null字段对于前端的展示来说是完全没有意义的，null作为未定义的意思是在程序级别范畴内的，在前端范畴它看到的null应该是空字符串
        // 对应于age年龄这种int类型的字段，我们可以定义-1为你没有填写年龄。
        // 然后我们在数据库当中处理null的时候，如果说会碰到一些特殊的情况，不得不使用isnotnull或者isnull这种方式处理就会非常恶心。
        // 为什么表设计时字段尽量使用not null，而不是所有情况都使用呢？
        // 假设telphone在这张表里边只能有一个（手机号是用户登录的唯一标识），一般会给telphone加上unque index的索引（即唯一索引）。
        // 但是这个唯一索引又会碰到什么问题呢？如果说我加了这个唯一索引后，当有第三方登录的情况(thirdpartyid)，以第三方登录的用户可能会没有手机号，telphone字段是空字符串
        // 此时数据库就遇到了一个非常尴尬的情况，telphone的确是唯一的，但是telphone设置了notnull，它必须在其他情况下面是一个空字符串的情况
        // 但是当我们指定了使用null的情况下，null是不受唯一索引约束的，就是我有两条一模一样telphone都是null的记录，是不会影响唯一索引的
        // 也就是说，当我们要求用户必须得要有手机号的时候，那我们就可以使用notnull，并且指定它对应的这个字段是一个唯一的key，
        // 那如果说对应的用户是不一定非得有手机号的，那我们这个字段指定notnull的话，对应的唯一索引就加不上去了，只能在应用程序内通过其他方式去解决。
        try {
            userDOMapper.insertSelective(userDO);
        }catch (DuplicateKeyException duplicateionKeyException){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号已重复注册");
        }
        userModel.setId(userDO.getId());
        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
        return;
    }

    private UserDO convertFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);

        return userDO;
    }

    private UserPasswordDO convertPasswordFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }

    @Override
    public UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException {
        // 通过用户的手机获取登录信息
        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        if(userDO == null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO,userPasswordDO);
        // 比对用户信息内加密的密码是否和传输进来的密码相匹配
        if(!StringUtils.equals(encrptPassword,userModel.getEncrptPassword())){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }
}
