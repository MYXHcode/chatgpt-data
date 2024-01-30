package com.myxh.chatgpt.data.trigger.http.dto;

import com.myxh.chatgpt.data.types.enums.ChatGPTModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author MYXH
 * @date 2024/1/29
 * @description ChatGPTRequestDTO ChatGPT 请求 DTO
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPTRequestDTO
{
    /**
     * 默认模型
     */
    private String model = ChatGPTModel.GPT_3_5_TURBO.getCode();

    /**
     * 问题描述
     */
    private List<MessageEntity> messages;
}
