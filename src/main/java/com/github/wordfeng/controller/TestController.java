package com.github.wordfeng.controller;

import com.github.wordfeng.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    private TestService testService;

    @RequestMapping("/test")
    public String a(@RequestParam Object any) {
        return testService.test(any);
    }
}
