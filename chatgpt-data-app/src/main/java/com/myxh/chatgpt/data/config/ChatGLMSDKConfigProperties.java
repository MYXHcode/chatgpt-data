package com.myxh.chatgpt.data.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author MYXH
 * @date 2024/3/1
 * @description ChatGLMSDKConfigProperties ChatGLMSDK 配置属性
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Data
@ConfigurationProperties(prefix = "chatglm.sdk.config", ignoreInvalidFields = true)
public class ChatGLMSDKConfigProperties
{
    /**
     * 状态：open = 开启、close 关闭
     */
    private boolean enable;

    /**
     * 转发地址
     */
    private String apiHost;

    /**
     * 可以申请 sk-***
     */
    private String apiSecretKey;
}
