package com.myxh.chatgpt.data.domain.openai.service.channel.model.impl;

import com.myxh.chatgpt.common.Constants;
import com.myxh.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import com.myxh.chatgpt.data.domain.openai.model.entity.MessageEntity;
import com.myxh.chatgpt.data.domain.openai.service.channel.model.IGenerativeModelService;
import com.myxh.chatgpt.domain.images.ImageEnum;
import com.myxh.chatgpt.domain.images.ImageRequest;
import com.myxh.chatgpt.domain.images.ImageResponse;
import com.myxh.chatgpt.domain.images.Item;
import com.myxh.chatgpt.session.OpenAiSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.List;

/**
 * @author MYXH
 * @date 2024/3/4
 * @description 图片生成
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Slf4j
@Service
public class ImageGenerativeModelServiceImpl implements IGenerativeModelService
{
    @Autowired(required = false)
    protected OpenAiSession chatGPTOpenAiSession;

    @Override
    public void doMessageResponse(ChatProcessAggregate chatProcess, ResponseBodyEmitter emitter) throws IOException
    {
        if (null == chatGPTOpenAiSession)
        {
            emitter.send("DALL-E 通道，模型调用未开启，可以选择其他模型对话！");

            return;
        }

        // 封装请求信息
        StringBuilder prompt = new StringBuilder();
        List<MessageEntity> messages = chatProcess.getMessages();

        for (MessageEntity message : messages)
        {
            String role = message.getRole();

            if (Constants.Role.USER.getCode().equals(role))
            {
                prompt.append(message.getContent());
                prompt.append("\r\n");
            }
        }

        // 绘图请求信息
        ImageRequest request = ImageRequest.builder()
                .prompt(prompt.toString())
                .model(chatProcess.getModel())
                .size(ImageEnum.Size.size_1024.getCode())
                .build();

        ImageResponse imageResponse = chatGPTOpenAiSession.genImages(request);
        List<Item> items = imageResponse.getData();

        for (Item item : items)
        {
            String url = item.getUrl();
            log.info("url:{}", url);
            emitter.send("![](" + url + ")");
        }

        emitter.complete();
    }
}
