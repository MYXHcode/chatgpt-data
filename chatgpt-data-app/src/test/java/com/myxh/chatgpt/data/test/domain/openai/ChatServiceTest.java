package com.myxh.chatgpt.data.test.domain.openai;

import com.myxh.chatgpt.common.Constants;
import com.myxh.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import com.myxh.chatgpt.data.domain.openai.model.entity.MessageEntity;
import com.myxh.chatgpt.data.domain.openai.service.IChatService;
import com.myxh.chatgpt.data.types.enums.ChatGPTModel;
import com.myxh.chatgpt.domain.chat.ChatCompletionRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

/**
 * @author MYXH
 * @date 2024/2/12
 * @description 对话测试
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatServiceTest
{
    @Resource
    private IChatService chatService;

    @Test
    public void testCompletions() throws InterruptedException
    {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        ChatProcessAggregate chatProcessAggregate = new ChatProcessAggregate();
        chatProcessAggregate.setOpenid("o0G6z6h-nHpZFUZVrcPJayOdN884");
        chatProcessAggregate.setModel(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode());
        chatProcessAggregate.setMessages(Collections.singletonList(MessageEntity.builder().role(Constants.Role.USER.getCode()).content("1+1").build()));

        ResponseBodyEmitter completions = chatService.completions(emitter, chatProcessAggregate);

        // 等待
        new CountDownLatch(1).await();
    }

    @Test
    public void testImagesCompletions() throws InterruptedException
    {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        ChatProcessAggregate chatProcessAggregate = new ChatProcessAggregate();
        chatProcessAggregate.setOpenid("o0G6z6h-nHpZFUZVrcPJayOdN884");
        chatProcessAggregate.setModel(ChatGPTModel.DALL_E_3.getCode());
        chatProcessAggregate.setMessages(Collections.singletonList(MessageEntity.builder().role(Constants.Role.USER.getCode()).content("画一条小狗").build()));

        ResponseBodyEmitter completions = chatService.completions(emitter, chatProcessAggregate);

        // 等待
        new CountDownLatch(1).await();
    }
}
