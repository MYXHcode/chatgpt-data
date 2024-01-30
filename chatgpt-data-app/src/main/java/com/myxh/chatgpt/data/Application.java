package com.myxh.chatgpt.data;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author MYXH
 * @date 2024/1/29
 * @description 启动类
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@SpringBootApplication
@Configurable
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class);
    }
}
