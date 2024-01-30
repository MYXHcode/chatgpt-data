package com.myxh.chatgpt.data.domain.openai.service;

import com.myxh.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/**
 * @author MYXH
 * @date 2024/1/29
 * @description ChatService 聊天服务
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
public interface IChatService
{
    ResponseBodyEmitter completions(ChatProcessAggregate chatProcess);
}
