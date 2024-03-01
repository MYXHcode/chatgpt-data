package com.myxh.chatgpt.data.domain.openai.service;

import com.myxh.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import com.myxh.chatgpt.data.domain.openai.model.entity.RuleLogicEntity;
import com.myxh.chatgpt.data.domain.openai.model.entity.UserAccountQuotaEntity;
import com.myxh.chatgpt.data.domain.openai.model.valobj.LogicCheckTypeVO;
import com.myxh.chatgpt.data.domain.openai.repository.IOpenAiRepository;
import com.myxh.chatgpt.data.domain.openai.service.channel.OpenAiGroupService;
import com.myxh.chatgpt.data.domain.openai.service.channel.impl.ChatGLMService;
import com.myxh.chatgpt.data.domain.openai.service.channel.impl.ChatGPTService;
import com.myxh.chatgpt.data.domain.openai.service.rule.factory.DefaultLogicFactory;
import com.myxh.chatgpt.data.types.common.Constants;
import com.myxh.chatgpt.data.types.enums.OpenAiChannel;
import com.myxh.chatgpt.data.types.exception.ChatGPTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MYXH
 * @date 2024/1/29
 * @description 对话模型抽象类
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Slf4j
public abstract class AbstractChatService implements IChatService
{
    private final Map<OpenAiChannel, OpenAiGroupService> openAiGroup = new HashMap<>();

    public AbstractChatService(ChatGPTService chatGPTService, ChatGLMService chatGLMService)
    {
        openAiGroup.put(OpenAiChannel.ChatGPT, chatGPTService);
        openAiGroup.put(OpenAiChannel.ChatGLM, chatGLMService);
    }

    @Resource
    private IOpenAiRepository openAiRepository;

    @Override
    public ResponseBodyEmitter completions(ResponseBodyEmitter emitter, ChatProcessAggregate chatProcess)
    {
        try
        {
            // 1. 请求应答
            emitter.onCompletion(() -> log.info("流式问答请求完成，使用模型：{}", chatProcess.getModel()));

            emitter.onError(throwable -> log.error("流式问答请求异常，使用模型：{}", chatProcess.getModel(), throwable));

            // 2. 账户获取
            UserAccountQuotaEntity userAccountQuotaEntity = openAiRepository.queryUserAccount(chatProcess.getOpenid());

            // 3. 规则过滤
            RuleLogicEntity<ChatProcessAggregate> ruleLogicEntity = this.doCheckLogic(chatProcess, userAccountQuotaEntity,
                    DefaultLogicFactory.LogicModel.ACCESS_LIMIT.getCode(),
                    DefaultLogicFactory.LogicModel.SENSITIVE_WORD.getCode(),
                    null != userAccountQuotaEntity ? DefaultLogicFactory.LogicModel.ACCOUNT_STATUS.getCode() : DefaultLogicFactory.LogicModel.NULL.getCode(),
                    null != userAccountQuotaEntity ? DefaultLogicFactory.LogicModel.MODEL_TYPE.getCode() : DefaultLogicFactory.LogicModel.NULL.getCode(),
                    null != userAccountQuotaEntity ? DefaultLogicFactory.LogicModel.USER_QUOTA.getCode() : DefaultLogicFactory.LogicModel.NULL.getCode()
            );

            if (!LogicCheckTypeVO.SUCCESS.equals(ruleLogicEntity.getType()))
            {
                emitter.send(ruleLogicEntity.getInfo());
                emitter.complete();

                return emitter;
            }

            // 4. 应答处理 【ChatGPT、ChatGLM 策略模式】
            openAiGroup.get(chatProcess.getChannel()).doMessageResponse(ruleLogicEntity.getData(), emitter);
        }
        catch (Exception e)
        {
            throw new ChatGPTException(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());
        }

        // 5. 返回结果
        return emitter;
    }

    protected abstract RuleLogicEntity<ChatProcessAggregate> doCheckLogic(ChatProcessAggregate chatProcess, UserAccountQuotaEntity userAccountQuotaEntity, String... logics) throws Exception;
}
