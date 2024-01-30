package com.myxh.chatgpt.data.trigger.http.dto;

import lombok.Data;

/**
 * @author MYXH
 * @date 2024/1/29
 * @description ChoiceEntity Choice 实体
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Data
public class ChoiceEntity
{
    /**
     * stream = true 请求参数里返回的属性是 delta
     */
    private MessageEntity delta;

    /**
     * stream = false 请求参数里返回的属性是 delta
     */
    private MessageEntity message;
}
