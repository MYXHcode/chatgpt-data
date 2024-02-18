package com.myxh.chatgpt.data.config;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.InputStream;

/**
 * @author MYXH
 * @date 2024/2/18
 * @description 微信支付服务配置
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(WeChatPayConfigProperties.class)
public class WeChatPayConfig
{
    /**
     * 一个商户号只能初始化一个配置，否则会因为重复的下载任务报错
     *
     * @param properties 支付配置
     * @return NativePay
     */
    @Bean
    @ConditionalOnProperty(value = "wxpay.config.enabled", havingValue = "true", matchIfMissing = false)
    public NativePayService buildNativePayService(WeChatPayConfigProperties properties)
    {
        // 支付配置
        Config config = new RSAAutoCertificateConfig.Builder()
                .merchantId(properties.getMchid())
                .privateKeyFromPath(getFilePath(properties.getPrivateKeyPath()))
                .merchantSerialNumber(properties.getMerchantSerialNumber())
                .apiV3Key(properties.getApiV3Key())
                .build();

        // NativePay 支付服务
        return new NativePayService.Builder().config(config).build();
    }

    @Bean
    @ConditionalOnProperty(value = "wxpay.config.enabled", havingValue = "true", matchIfMissing = false)
    public NotificationConfig buildNotificationConfig(WeChatPayConfigProperties properties)
    {
        return new RSAAutoCertificateConfig.Builder()
                .merchantId(properties.getMchid())
                .privateKeyFromPath(getFilePath(properties.getPrivateKeyPath()))
                .merchantSerialNumber(properties.getMerchantSerialNumber())
                .apiV3Key(properties.getApiV3Key())
                .build();
    }

    @Bean
    @ConditionalOnBean(NotificationConfig.class)
    @ConditionalOnProperty(value = "wxpay.config.enabled", havingValue = "true", matchIfMissing = false)
    public NotificationParser buildNotificationParser(NotificationConfig notificationConfig)
    {
        return new NotificationParser(notificationConfig);
    }

    public static String getFilePath(String classFilePath)
    {
        String filePath = "";

        try
        {
            String templateFilePath = "tempfiles/classpathfile/";
            File tempDir = new File(templateFilePath);

            if (!tempDir.exists())
            {
                tempDir.mkdirs();
            }

            String[] filePathList = classFilePath.split("/");
            StringBuilder checkFilePath = new StringBuilder("tempfiles/classpathfile");

            for (String item : filePathList)
            {
                checkFilePath.append("/").append(item);
            }

            File tempFile = new File(checkFilePath.toString());

            if (tempFile.exists())
            {
                filePath = checkFilePath.toString();
            }
            else
            {
                // 解析
                ClassPathResource classPathResource = new ClassPathResource(classFilePath);
                InputStream inputStream = classPathResource.getInputStream();
                checkFilePath = new StringBuilder("tempfiles/classpathfile");

                for (int i = 0; i < filePathList.length; i++)
                {
                    checkFilePath.append("/").append(filePathList[i]);

                    if (i == filePathList.length - 1)
                    {
                        // 文件
                        File file = new File(checkFilePath.toString());

                        if (!file.exists())
                        {
                            FileUtils.copyInputStreamToFile(inputStream, file);
                        }
                    }
                    else
                    {
                        // 目录
                        tempDir = new File(checkFilePath.toString());

                        if (!tempDir.exists())
                        {
                            tempDir.mkdirs();
                        }
                    }
                }

                inputStream.close();
                filePath = checkFilePath.toString();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return filePath;
    }
}
