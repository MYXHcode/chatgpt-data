package com.myxh.chatgpt.data.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author MYXH
 * @date 2024/3/3
 * @description Redis 连接配置 <a href="https://github.com/redisson/redisson/tree/master/redisson-spring-boot-starter">redisson-spring-boot-starter</a>
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Data
@ConfigurationProperties(prefix = "redis.sdk.config", ignoreInvalidFields = true)
public class RedisClientConfigProperties
{
    /**
     * host:ip
     */
    private String host;

    /**
     * 端口
     */
    private int port;

    /**
     * 账密
     */
    private String password;

    /**
     * 设置连接池的大小，默认为 64
     */
    private int poolSize = 64;

    /**
     * 设置连接池的最小空闲连接数，默认为 10
     */
    private int minIdleSize = 10;

    /**
     * 设置连接的最大空闲时间（单位：毫秒），超过该时间的空闲连接将被关闭，默认为 10000
     */
    private int idleTimeout = 10000;

    /**
     * 设置连接超时时间（单位：毫秒），默认为 10000
     */
    private int connectTimeout = 10000;

    /**
     * 设置连接重试次数，默认为 3
     */
    private int retryAttempts = 3;

    /**
     * 设置连接重试的间隔时间（单位：毫秒），默认为 1000
     */
    private int retryInterval = 1000;

    /**
     * 设置定期检查连接是否可用的时间间隔（单位：毫秒），默认为 0，表示不进行定期检查
     */
    private int pingInterval = 0;

    /**
     * 设置是否保持长连接，默认为 true
     */
    private boolean keepAlive = true;
}
