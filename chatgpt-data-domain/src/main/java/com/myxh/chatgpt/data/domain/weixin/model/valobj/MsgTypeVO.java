package com.myxh.chatgpt.data.domain.weixin.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author MYXH
 * @date 2024/1/30
 * @description 微信公众号消息类型值对象，用于描述对象属性的值，为值对象
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum MsgTypeVO
{
    EVENT("event", "事件消息"),
    TEXT("text", "文本消息");

    private String code;
    private String desc;
}
