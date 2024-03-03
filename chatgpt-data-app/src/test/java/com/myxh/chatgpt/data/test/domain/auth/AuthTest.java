package com.myxh.chatgpt.data.test.domain.auth;

import com.alibaba.fastjson2.JSON;
import com.google.common.cache.Cache;
import com.myxh.chatgpt.data.domain.auth.model.entity.AuthStateEntity;
import com.myxh.chatgpt.data.domain.auth.service.IAuthService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author MYXH
 * @date 2024/1/30
 * @description AuthTest Auth 测试
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTest
{
    @Resource
    private IAuthService authService;

    @Resource
    private Cache<String, String> cache;

    @Test
    public void testAuthService()
    {
        cache.put("1000", "MYXH");
        cache.put("MYXH", "1000");

        AuthStateEntity authStateEntity = authService.doLogin("1000");
        log.info(JSON.toJSONString(authStateEntity));

        authService.checkToken(authStateEntity.getToken());
    }
}
