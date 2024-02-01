package com.myxh.chatgpt.data.domain.weixin.service;

import com.myxh.chatgpt.data.domain.weixin.model.entity.UserBehaviorMessageEntity;

/**
 * @author MYXH
 * @date 2024/1/30
 * @description 受理用户行为接口
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
public interface IWeiXinBehaviorService
{
    String acceptUserBehavior(UserBehaviorMessageEntity userBehaviorMessageEntity);
}
