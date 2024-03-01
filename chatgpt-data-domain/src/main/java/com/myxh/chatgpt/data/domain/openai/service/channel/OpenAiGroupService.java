package com.myxh.chatgpt.data.domain.openai.service.channel;

import com.myxh.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/**
 * @author MYXH
 * @date 2024/3/1
 * @description 服务组
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
public interface OpenAiGroupService
{
    void doMessageResponse(ChatProcessAggregate chatProcess, ResponseBodyEmitter emitter) throws Exception;
}
