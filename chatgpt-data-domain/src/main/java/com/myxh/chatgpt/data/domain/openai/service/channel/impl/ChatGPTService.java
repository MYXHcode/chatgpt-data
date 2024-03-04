package com.myxh.chatgpt.data.domain.openai.service.channel.impl;

import com.myxh.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import com.myxh.chatgpt.data.domain.openai.model.valobj.GenerativeModelVO;
import com.myxh.chatgpt.data.domain.openai.service.channel.OpenAiGroupService;
import com.myxh.chatgpt.data.domain.openai.service.channel.model.IGenerativeModelService;
import com.myxh.chatgpt.data.domain.openai.service.channel.model.impl.ImageGenerativeModelServiceImpl;
import com.myxh.chatgpt.data.domain.openai.service.channel.model.impl.TextGenerativeModelServiceImpl;
import com.myxh.chatgpt.session.OpenAiSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MYXH
 * @date 2024/3/1
 * @description ChatGPT 服务
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Service
public class ChatGPTService implements OpenAiGroupService
{
    @Autowired(required = false)
    protected OpenAiSession chatGPTOpenAiSession;

    private final Map<GenerativeModelVO, IGenerativeModelService> generativeModelGroup = new HashMap<>();

    public ChatGPTService(ImageGenerativeModelServiceImpl imageGenerativeModelService, TextGenerativeModelServiceImpl textGenerativeModelService)
    {
        generativeModelGroup.put(GenerativeModelVO.IMAGES, imageGenerativeModelService);
        generativeModelGroup.put(GenerativeModelVO.TEXT, textGenerativeModelService);
    }

    @Override
    public void doMessageResponse(ChatProcessAggregate chatProcess, ResponseBodyEmitter emitter) throws IOException
    {
        GenerativeModelVO generativeModelVO = chatProcess.getGenerativeModelVO();
        generativeModelGroup.get(generativeModelVO).doMessageResponse(chatProcess, emitter);
    }
}
