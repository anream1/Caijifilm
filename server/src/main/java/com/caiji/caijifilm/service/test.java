package com.caiji.caijifilm.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/start")
@RequestMapping(value = "/start")
public class test {

    @RequestMapping("/hello")
    public String index() {
        System.out.println("---------开始----------");
        return "Hello World";
    }


//    @RequestMapping("/getUser")
//    public User getUser() {
//        System.out.println("---------开始----------");
//        User user=new User();
//        user.setId(2);
//        user.setName("李四");
//        return user;
//    }


}