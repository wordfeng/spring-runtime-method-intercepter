package com.github.wordfeng.service;

import com.github.wordfeng.intercepter.LogParamsAndReturn;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Override
    @LogParamsAndReturn
    public String test(Object object) {
        return "" + object;
    }
}
