package com.myxh.chatgpt.data.domain.weixin.service.validate;

import com.myxh.chatgpt.data.domain.weixin.service.IWeiXinValidateService;
import com.myxh.chatgpt.data.types.sdk.weixin.SignatureUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author MYXH
 * @date 2024/1/30
 * @description 验签接口实现
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Service
public class WeiXinValidateService implements IWeiXinValidateService
{

    @Value("${wx.config.token}")
    private String token;

    @Override
    public boolean checkSign(String signature, String timestamp, String nonce)
    {
        return SignatureUtil.check(token, signature, timestamp, nonce);
    }
}
