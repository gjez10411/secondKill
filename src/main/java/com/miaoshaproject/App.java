package com.miaoshaproject;

import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
// 把这个启动类当做一个可以自动化，支持配置的bean，并且能够开启整个一个工程类的基于springboot的一个配置;它会帮我们启动一个内嵌的tomcat并且加载进去默认的配置
// @EnableAutoConfiguration
// 让springboot扫描mybatis的配置文件，就是DOMapper和后续Service的一些封装
// 和@EnableAutoConfiguration异曲同工，让App这个类被Spring托管，并且可以指定它是一个主启动类
@SpringBootApplication(scanBasePackages = {"com.miaoshaproject"})
// 充当spring mvc中controller的功能
@RestController
// 把DAO存放的地方设置在该注解内
@MapperScan("com.miaoshaproject.dao")
public class App 
{
    @Autowired
    private UserDOMapper userDOMapper;
    // 和@RestController一起实现spring mvc之前要配置servlet web.xml等等复杂的功能
    @RequestMapping("/")
    public String home(){
        UserDO userDO = userDOMapper.selectByPrimaryKey(1);
        if(userDO==null){
            return "用户对象不存在";
        }else{
            return userDO.getName();
        }
    }
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        // 一旦开启EnableAutoConfiguration之后需要用一行代码来启动Springboot项目
        SpringApplication.run(App.class,args);
    }
}
