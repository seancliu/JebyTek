package com.jebytek.server.service;

import com.jebytek.server.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestService {
    @Resource
    private TestMapper testMapper;
//    public List<Test> list() {
//        return testMapper.list();
//    }
}
