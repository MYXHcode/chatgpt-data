package com.myxh.chatgpt.data.infrastructure.repository;

import com.myxh.chatgpt.data.domain.auth.repository.IAuthRepository;
import com.myxh.chatgpt.data.infrastructure.redis.IRedisService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author MYXH
 * @date 2024/3/3
 * @description 认证仓储服务
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Repository
public class AuthRepository implements IAuthRepository
{
    private static final String Key = "weixin_code";

    @Resource
    private IRedisService redisService;

    @Override
    public String getCodeUserOpenId(String code)
    {
        return redisService.getValue(Key + "_" + code);
    }

    @Override
    public void removeCodeByOpenId(String code, String openId)
    {
        redisService.remove(Key + "_" + code);
        redisService.remove(Key + "_" + openId);
    }
}
