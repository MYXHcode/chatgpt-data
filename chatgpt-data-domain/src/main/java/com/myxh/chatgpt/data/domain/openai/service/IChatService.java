package com.myxh.chatgpt.data.domain.openai.service;

import com.myxh.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/**
 * @author MYXH
 * @date 2024/1/29
 * @description OpenAi 应答请求
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
public interface IChatService
{
    ResponseBodyEmitter completions(ResponseBodyEmitter emitter, ChatProcessAggregate chatProcess);
}
