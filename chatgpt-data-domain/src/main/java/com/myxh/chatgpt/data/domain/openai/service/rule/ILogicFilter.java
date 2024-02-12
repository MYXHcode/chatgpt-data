package com.myxh.chatgpt.data.domain.openai.service.rule;

import com.myxh.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import com.myxh.chatgpt.data.domain.openai.model.entity.RuleLogicEntity;

/**
 * @author MYXH
 * @date 2024/2/3
 * @description 规则过滤接口
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
public interface ILogicFilter<T>
{
    RuleLogicEntity<ChatProcessAggregate> filter(ChatProcessAggregate chatProcess, T data) throws Exception;
}
