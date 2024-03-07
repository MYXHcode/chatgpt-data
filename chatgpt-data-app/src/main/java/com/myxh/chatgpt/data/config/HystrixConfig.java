package com.myxh.chatgpt.data.config;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MYXH
 * @date 2024/3/6
 * @description HystrixConfig Hystrix 配置
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Configuration
public class HystrixConfig
{
    @Bean
    public HystrixCommandAspect hystrixCommandAspect()
    {
        return new HystrixCommandAspect();
    }
}
