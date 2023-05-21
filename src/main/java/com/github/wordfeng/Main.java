package com.github.wordfeng;

import com.github.wordfeng.service.TestService;
import com.github.wordfeng.service.TestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
        log.info("dsadas");
        new TestServiceImpl().test("dsadas");
    }
}
