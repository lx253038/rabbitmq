package com.mq.rabbit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author LX
 * @Date 2019-8-28 10:39
 * @Description TODO
 */
@RestController
public class TestController {

    @Autowired
    private Sender sender;

    @GetMapping("hello")
    public String helloTest() {
        sender.send();
        return "success";
    }

    @GetMapping("hello2")
    public String helloTest2() {
        sender.send2();
        return "success";
    }

}