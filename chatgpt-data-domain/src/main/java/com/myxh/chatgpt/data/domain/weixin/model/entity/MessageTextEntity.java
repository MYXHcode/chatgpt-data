package com.myxh.chatgpt.data.domain.weixin.model.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * @author MYXH
 * @date 2024/1/30
 * @description 消息实体
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Setter
@Getter
public class MessageTextEntity
{
    @XStreamAlias("MsgId")
    private String msgId;

    @XStreamAlias("ToUserName")
    private String toUserName;

    @XStreamAlias("FromUserName")
    private String fromUserName;

    @XStreamAlias("CreateTime")
    private String createTime;

    @XStreamAlias("MsgType")
    private String msgType;

    @XStreamAlias("Content")
    private String content;

    @XStreamAlias("Event")
    private String event;

    @XStreamAlias("EventKey")
    private String eventKey;

    public MessageTextEntity()
    {

    }
}
