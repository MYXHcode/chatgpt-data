package com.myxh.chatgpt.data.domain.weixin.repository;

/**
 * @author MYXH
 * @date 2024/3/3
 * @description 微信服务仓储
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
public interface IWeiXinRepository
{
    String genCode(String openId);
}
