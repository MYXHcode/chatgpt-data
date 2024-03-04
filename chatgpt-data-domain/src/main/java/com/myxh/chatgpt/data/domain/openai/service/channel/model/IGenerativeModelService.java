package com.myxh.chatgpt.data.domain.openai.service.channel.model;

import com.myxh.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;

/**
 * @author MYXH
 * @date 2024/3/4
 * @description 模型生成文字/图片接口服务
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
public interface IGenerativeModelService
{
    void doMessageResponse(ChatProcessAggregate chatProcess, ResponseBodyEmitter emitter) throws IOException;
}
