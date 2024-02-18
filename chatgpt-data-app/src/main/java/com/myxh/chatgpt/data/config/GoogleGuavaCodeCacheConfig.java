package com.myxh.chatgpt.data.config;

import com.myxh.chatgpt.data.trigger.mq.OrderPaySuccessListener;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.TimeUnit;

/**
 * @author MYXH
 * @date 2024/1/30
 * @description GoogleGuavaCodeCacheConfig Google Guava 验证码缓存配置
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Configuration
public class GoogleGuavaCodeCacheConfig
{
    @Bean(name = "codeCache")
    public Cache<String, String> codeCache()
    {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(3, TimeUnit.MINUTES)
                .build();
    }

    @Bean(name = "visitCache")
    public Cache<String, Integer> visitCache()
    {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(12, TimeUnit.HOURS)
                .build();
    }

    @Bean
    public EventBus eventBusListener(OrderPaySuccessListener listener)
    {
        EventBus eventBus = new EventBus();
        eventBus.register(listener);

        return eventBus;
    }
}
