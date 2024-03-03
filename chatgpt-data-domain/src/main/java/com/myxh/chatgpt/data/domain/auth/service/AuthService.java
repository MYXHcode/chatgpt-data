package com.myxh.chatgpt.data.domain.auth.service;

import com.google.common.cache.Cache;
import com.myxh.chatgpt.data.domain.auth.model.entity.AuthStateEntity;
import com.myxh.chatgpt.data.domain.auth.model.valobj.AuthTypeVO;
import com.myxh.chatgpt.data.domain.auth.repository.IAuthRepository;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author MYXH
 * @date 2024/1/30
 * @description 鉴权服务
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Slf4j
@Service
public class AuthService extends AbstractAuthService
{
    @Resource
    private Cache<String, String> codeCache;

    @Resource
    private IAuthRepository repository;

    @Override
    protected AuthStateEntity checkCode(String code)
    {
        // 获取验证码校验
        String openId = repository.getCodeUserOpenId(code);

        if (StringUtils.isBlank(openId))
        {
            log.info("鉴权，用户收入的验证码不存在 {}", code);

            return AuthStateEntity.builder()
                    .code(AuthTypeVO.A0001.getCode())
                    .info(AuthTypeVO.A0001.getInfo())
                    .build();
        }

        // 移除缓存 Key 值
        repository.removeCodeByOpenId(code, openId);

        // 验证码校验成功
        return AuthStateEntity.builder()
                .code(AuthTypeVO.A0000.getCode())
                .info(AuthTypeVO.A0000.getInfo())
                .openId(openId)
                .build();
    }

    @Override
    public boolean checkToken(String token)
    {
        return isVerify(token);
    }

    @Override
    public String openid(String token)
    {
        Claims claims = decode(token);

        return claims.get("openId").toString();
    }
}
