package com.myxh.chatgpt.data.config;

import com.myxh.chatglm.session.OpenAiSession;
import com.myxh.chatglm.session.OpenAiSessionFactory;
import com.myxh.chatglm.session.defaults.DefaultOpenAiSessionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MYXH
 * @date 2024/3/1
 * @description ChatGLMSDKConfig 开启工厂配置
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Configuration
@EnableConfigurationProperties(ChatGLMSDKConfigProperties.class)
public class ChatGLMSDKConfig
{
    @Bean(name = "chatGlMOpenAiSession")
    @ConditionalOnProperty(value = "chatglm.sdk.config.enabled", havingValue = "true", matchIfMissing = false)
    public OpenAiSession openAiSession(ChatGLMSDKConfigProperties properties)
    {
        // 1. 配置文件
        com.myxh.chatglm.session.Configuration configuration = new com.myxh.chatglm.session.Configuration();
        configuration.setApiHost(properties.getApiHost());
        configuration.setApiSecretKey(properties.getApiSecretKey());

        // 2. 会话工厂
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);

        // 3. 开启会话
        return factory.openSession();
    }
}
