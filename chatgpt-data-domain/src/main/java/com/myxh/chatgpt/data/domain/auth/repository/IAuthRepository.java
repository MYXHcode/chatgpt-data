package com.myxh.chatgpt.data.domain.auth.repository;

/**
 * @author MYXH
 * @date 2024/3/3
 * @description 认证仓储服务
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
public interface IAuthRepository
{
    String getCodeUserOpenId(String code);

    void removeCodeByOpenId(String code, String openId);
}
