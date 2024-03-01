package com.myxh.chatgpt.data.domain.openai.service;

import com.myxh.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import com.myxh.chatgpt.data.domain.openai.model.entity.RuleLogicEntity;
import com.myxh.chatgpt.data.domain.openai.model.entity.UserAccountQuotaEntity;
import com.myxh.chatgpt.data.domain.openai.model.valobj.LogicCheckTypeVO;
import com.myxh.chatgpt.data.domain.openai.service.channel.impl.ChatGLMService;
import com.myxh.chatgpt.data.domain.openai.service.channel.impl.ChatGPTService;
import com.myxh.chatgpt.data.domain.openai.service.rule.ILogicFilter;
import com.myxh.chatgpt.data.domain.openai.service.rule.factory.DefaultLogicFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author MYXH
 * @date 2024/1/29
 * @description ChatService 聊天服务
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Service
public class ChatService extends AbstractChatService
{
    @Resource
    private DefaultLogicFactory logicFactory;

    public ChatService(ChatGPTService chatGPTService, ChatGLMService chatGLMService)
    {
        super(chatGPTService, chatGLMService);
    }

    @Override
    protected RuleLogicEntity<ChatProcessAggregate> doCheckLogic(ChatProcessAggregate chatProcess, UserAccountQuotaEntity userAccountQuotaEntity, String... logics) throws Exception
    {
        Map<String, ILogicFilter<UserAccountQuotaEntity>> logicFilterMap = logicFactory.openLogicFilter();
        RuleLogicEntity<ChatProcessAggregate> entity = null;

        for (String code : logics)
        {
            if (DefaultLogicFactory.LogicModel.NULL.getCode().equals(code))
            {
                continue;
            }

            entity = logicFilterMap.get(code).filter(chatProcess, userAccountQuotaEntity);

            if (!LogicCheckTypeVO.SUCCESS.equals(entity.getType()))
            {
                return entity;
            }
        }

        return entity != null ? entity : RuleLogicEntity.<ChatProcessAggregate>builder()
                .type(LogicCheckTypeVO.SUCCESS).data(chatProcess).build();
    }
}
