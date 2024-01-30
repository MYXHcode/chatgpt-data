package com.myxh.chatgpt.data.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author MYXH
 * @date 2024/1/29
 * @description ChatGPTSDKConfigProperties ChatGPTSDK 配置属性
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Data
@ConfigurationProperties(prefix = "chatgpt.sdk.config", ignoreInvalidFields = true)
public class ChatGPTSDKConfigProperties
{
    /**
     * 转发地址 <a href="https://api.xfg.im/b8b6/">https://api.xfg.im/b8b6/</a>
     */
    private String apiHost;

    /**
     * 可以申请 sk-***
     */
    private String apiKey;

    /**
     * 获取Token <a href="http://api.xfg.im:8080/authorize?username=xfg&password=123">访问获取</a>
     */
    private String authToken;
}
