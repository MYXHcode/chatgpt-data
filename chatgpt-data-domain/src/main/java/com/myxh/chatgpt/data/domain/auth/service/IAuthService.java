package com.myxh.chatgpt.data.domain.auth.service;

import com.myxh.chatgpt.data.domain.auth.model.entity.AuthStateEntity;

/**
 * @author MYXH
 * @date 2024/1/30
 * @description 鉴权验证服务接口
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
public interface IAuthService
{
    /**
     * 登录验证
     *
     * @param code 验证码
     * @return Token
     */
    AuthStateEntity doLogin(String code);

    boolean checkToken(String token);

    String openid(String token);
}
