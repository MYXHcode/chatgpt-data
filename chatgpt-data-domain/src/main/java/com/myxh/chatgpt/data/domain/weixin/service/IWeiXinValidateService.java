package com.myxh.chatgpt.data.domain.weixin.service;

/**
 * @author MYXH
 * @date 2024/1/30
 * @description 验签接口
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
public interface IWeiXinValidateService
{
    boolean checkSign(String signature, String timestamp, String nonce);
}
