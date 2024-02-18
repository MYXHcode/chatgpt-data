package com.myxh.chatgpt.data.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author MYXH
 * @date 2024/2/18
 * @description 微信支付配置
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Data
@ConfigurationProperties(prefix = "wxpay.config", ignoreInvalidFields = true)
public class WeChatPayConfigProperties
{
    /**
     * 状态：open = 开启、close 关闭
     */
    private boolean enable;

    /**
     * 申请支付主体的 appid
     */
    private String appid;

    /**
     * 商户号
     */
    private String mchid;

    /**
     * 回调地址
     */
    private String notifyUrl;

    /**
     * 商户 API 私钥路径
     */
    private String privateKeyPath;

    /**
     * 商户证书序列号：openssl x509 -in apiclient_cert.pem -noout -serial
     */
    private String merchantSerialNumber;

    /**
     * 商户 APIV3 密钥
     */
    private String apiV3Key;

    /*
    public boolean isEnable()
    {
        return "open".equals(enable);
    }
     */
}
