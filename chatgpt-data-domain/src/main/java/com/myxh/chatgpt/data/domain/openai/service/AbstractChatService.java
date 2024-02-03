package com.myxh.chatgpt.data.domain.openai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myxh.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import com.myxh.chatgpt.data.domain.openai.model.entity.RuleLogicEntity;
import com.myxh.chatgpt.data.domain.openai.model.valobj.LogicCheckTypeVO;
import com.myxh.chatgpt.data.domain.openai.service.rule.factory.DefaultLogicFactory;
import com.myxh.chatgpt.data.types.common.Constants;
import com.myxh.chatgpt.data.types.exception.ChatGPTException;
import com.myxh.chatgpt.session.OpenAiSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.annotation.Resource;

/**
 * @author MYXH
 * @date 2024/1/29
 * @description 对话模型抽象类
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Slf4j
public abstract class AbstractChatService implements IChatService
{
    @Resource
    protected OpenAiSession openAiSession;

    @Override
    public ResponseBodyEmitter completions(ResponseBodyEmitter emitter, ChatProcessAggregate chatProcess)
    {
        try
        {
            // 1. 请求应答
            emitter.onCompletion(() -> log.info("流式问答请求完成，使用模型：{}", chatProcess.getModel()));
            emitter.onError(throwable -> log.error("流式问答请求疫情，使用模型：{}", chatProcess.getModel(), throwable));

            // 2. 规则过滤
            RuleLogicEntity<ChatProcessAggregate> ruleLogicEntity = this.doCheckLogic(chatProcess,
                    DefaultLogicFactory.LogicModel.ACCESS_LIMIT.getCode(),
                    DefaultLogicFactory.LogicModel.SENSITIVE_WORD.getCode());

            if (!LogicCheckTypeVO.SUCCESS.equals(ruleLogicEntity.getType()))
            {
                emitter.send(ruleLogicEntity.getInfo());
                emitter.complete();

                return emitter;
            }

            // 3. 应答处理
            this.doMessageResponse(ruleLogicEntity.getData(), emitter);
        }
        catch (Exception e)
        {
            throw new ChatGPTException(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());
        }

        // 3. 返回结果
        return emitter;
    }

    protected abstract RuleLogicEntity<ChatProcessAggregate> doCheckLogic(ChatProcessAggregate chatProcess, String... logics) throws Exception;

    protected abstract void doMessageResponse(ChatProcessAggregate chatProcess, ResponseBodyEmitter responseBodyEmitter) throws JsonProcessingException;
}
