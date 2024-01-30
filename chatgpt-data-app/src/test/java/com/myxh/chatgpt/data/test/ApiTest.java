package com.myxh.chatgpt.data.test;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.myxh.chatgpt.common.Constants;
import com.myxh.chatgpt.data.trigger.http.dto.ChatGPTRequestDTO;
import com.myxh.chatgpt.data.trigger.http.dto.MessageEntity;
import com.myxh.chatgpt.data.types.enums.ChatGPTModel;
import com.myxh.chatgpt.domain.chat.ChatCompletionRequest;
import com.myxh.chatgpt.domain.chat.ChatCompletionResponse;
import com.myxh.chatgpt.domain.chat.Message;
import com.myxh.chatgpt.session.OpenAiSession;
import lombok.extern.slf4j.Slf4j;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author MYXH
 * @date 2024/1/29
 * @description 测试工程
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest
{
    @Resource
    private OpenAiSession openAiSession;

    /**
     * 此对话模型 3.5 接近于官网体验
     */
    @Test
    public void testChatCompletions()
    {
        // 1. 创建参数
        ChatCompletionRequest chatCompletion = ChatCompletionRequest
                .builder()
                .messages(Collections.singletonList(Message.builder().role(Constants.Role.USER).content("写一个java冒泡排序").build()))
                .model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode())
                .build();

        // 2. 发起请求
        ChatCompletionResponse chatCompletionResponse = openAiSession.completions(chatCompletion);

        // 3. 解析结果
        chatCompletionResponse.getChoices().forEach(e -> log.info("测试结果：{}", e.getMessage()));
    }

    /**
     * 此对话模型 3.5 接近于官网体验 & 流式应答
     */
    @Test
    public void testChatCompletionsStream() throws JsonProcessingException, InterruptedException
    {
        // 1. 创建参数
        ChatCompletionRequest chatCompletion = ChatCompletionRequest
                .builder()
                .stream(true)
                .messages(Collections.singletonList(Message.builder().role(Constants.Role.USER).content("写一个java冒泡排序").build()))
                .model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode())
                .build();

        // 2. 发起请求
        EventSource eventSource = openAiSession.chatCompletions(chatCompletion, new EventSourceListener()
        {
            @Override
            public void onEvent(EventSource eventSource, String id, String type, String data)
            {
                log.info("测试结果：{}", data);
            }
        });

        // 等待
        new CountDownLatch(1).await();
    }

    @Test
    public void testRequestJson()
    {

        List<MessageEntity> messages = new ArrayList<>();


        MessageEntity messageEntity1 = new MessageEntity();
        messageEntity1.setRole(Constants.Role.USER.getCode());
        messageEntity1.setContent("你是一个非常专业的 Java 开发工程师，具备世界一流水平，曾在多家世界级互联网公司任职，可以写出非常优秀易于扩展和迭代的代码。");

        MessageEntity messageEntity2 = new MessageEntity();
        messageEntity2.setRole(Constants.Role.USER.getCode());
        messageEntity2.setContent("写一个java冒泡排序");

        messages.add(messageEntity1);
        messages.add(messageEntity2);

        ChatGPTRequestDTO requestDTO = ChatGPTRequestDTO.builder().model(ChatGPTModel.GPT_3_5_TURBO.getCode()).messages(messages).build();

        // {"messages":[{"content":"你是一个非常专业的 Java 开发工程师，具备世界一流水平，曾在多家世界级互联网公司任职，可以写出非常优秀易于扩展和迭代的代码。","role":"user"},{"content":"写一个java冒泡排序","role":"user"}],"model":"gpt-3.5-turbo"}
        log.info(JSON.toJSONString(requestDTO));

        String str = "{\"messages\":[{\"content\":\"写一个java冒泡排序\",\"role\":\"user\"}],\"model\":\"gpt-3.5-turbo\"}";
    }
}
