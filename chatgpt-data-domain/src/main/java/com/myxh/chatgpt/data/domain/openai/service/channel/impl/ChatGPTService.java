package com.myxh.chatgpt.data.domain.openai.service.channel.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.myxh.chatgpt.common.Constants;
import com.myxh.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import com.myxh.chatgpt.data.domain.openai.service.channel.OpenAiGroupService;
import com.myxh.chatgpt.data.types.exception.ChatGPTException;
import com.myxh.chatgpt.domain.chat.ChatChoice;
import com.myxh.chatgpt.domain.chat.ChatCompletionRequest;
import com.myxh.chatgpt.domain.chat.ChatCompletionResponse;
import com.myxh.chatgpt.domain.chat.Message;
import com.myxh.chatgpt.session.OpenAiSession;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MYXH
 * @date 2024/3/1
 * @description ChatGPT 服务
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Service
public class ChatGPTService implements OpenAiGroupService
{
    @Resource
    protected OpenAiSession chatGPTOpenAiSession;

    @Override
    public void doMessageResponse(ChatProcessAggregate chatProcess, ResponseBodyEmitter emitter) throws JsonProcessingException
    {
        // 1. 请求消息
        List<Message> messages = chatProcess.getMessages().stream()
                .map(entity -> Message.builder()
                        .role(Constants.Role.valueOf(entity.getRole().toUpperCase()))
                        .content(entity.getContent())
                        .name(entity.getName())
                        .build())
                .collect(Collectors.toList());

        // 2. 封装参数
        ChatCompletionRequest chatCompletion = ChatCompletionRequest
                .builder()
                .stream(true)
                .messages(messages)
                .model(chatProcess.getModel())
                .build();

        // 3. 请求应答
        chatGPTOpenAiSession.chatCompletions(chatCompletion, new EventSourceListener()
        {
            @Override
            public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data)
            {
                ChatCompletionResponse chatCompletionResponse = JSON.parseObject(data, ChatCompletionResponse.class);
                List<ChatChoice> choices = chatCompletionResponse.getChoices();

                for (ChatChoice chatChoice : choices)
                {
                    Message delta = chatChoice.getDelta();

                    if (Constants.Role.ASSISTANT.getCode().equals(delta.getRole()))
                    {
                        continue;
                    }

                    // 应答完成
                    String finishReason = chatChoice.getFinishReason();

                    if (StringUtils.isNoneBlank(finishReason) && "stop".equals(finishReason))
                    {
                        emitter.complete();
                        break;
                    }

                    // 发送信息
                    try
                    {
                        emitter.send(delta.getContent());
                    }
                    catch (Exception e)
                    {
                        throw new ChatGPTException(e.getMessage());
                    }
                }
            }
        });
    }
}
