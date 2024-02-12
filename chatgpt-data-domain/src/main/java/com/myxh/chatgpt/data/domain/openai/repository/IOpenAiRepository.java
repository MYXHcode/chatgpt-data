package com.myxh.chatgpt.data.domain.openai.repository;

import com.myxh.chatgpt.data.domain.openai.model.entity.UserAccountQuotaEntity;

/**
 * @author MYXH
 * @date 2024/2/12
 * @description OpenAi 仓储接口
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
public interface IOpenAiRepository
{
    int subAccountQuota(String openai);

    UserAccountQuotaEntity queryUserAccount(String openid);
}
