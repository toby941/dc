/*
 * Copyright 2011 Mitian Technology, Co., Ltd. All rights reserved.
 */
package com.dc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/test.xml")
/**
 * 继承AbstractTransactionalJUnit4SpringContextTests，当测试执行完毕后执行数据库回滚
 */
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Test
    public void base_test() {
        System.out.println(getClass() + "  test run");
    }
}
