package com.myxh.chatgpt.data.config;

import com.myxh.chatgpt.session.OpenAiSession;
import com.myxh.chatgpt.session.OpenAiSessionFactory;
import com.myxh.chatgpt.session.defaults.DefaultOpenAiSessionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MYXH
 * @date 2024/1/29
 * @description OpenAiSession 开启工厂配置
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Configuration
@EnableConfigurationProperties(ChatGPTSDKConfigProperties.class)
public class ChatGPTSDKConfig
{
    @Bean(name = "chatGPTOpenAiSession")
    @ConditionalOnProperty(value = "chatgpt.sdk.config.enabled", havingValue = "true", matchIfMissing = false)
    public OpenAiSession openAiSession(ChatGPTSDKConfigProperties properties)
    {
        // 1. 配置文件
        com.myxh.chatgpt.session.Configuration configuration = new com.myxh.chatgpt.session.Configuration();
        configuration.setApiHost(properties.getApiHost());
        configuration.setAuthToken(properties.getAuthToken());
        configuration.setApiKey(properties.getApiKey());

        // 2. 会话工厂
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);

        // 3. 开启会话
        return factory.openSession();
    }
}
