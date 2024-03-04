package com.myxh.chatgpt.data.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MYXH
 * @date 2024/3/1
 * @description OpenAi 渠道
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Getter
@AllArgsConstructor
public enum OpenAiChannel
{
    ChatGLM("ChatGLM"),
    ChatGPT("ChatGPT"),
    ;

    private final String code;

    public static OpenAiChannel getChannel(String model)
    {
        if (model.toLowerCase().contains("gpt") || model.toLowerCase().contains("dall"))
        {
            return OpenAiChannel.ChatGPT;
        }

        if (model.toLowerCase().contains("glm"))
        {
            return OpenAiChannel.ChatGLM;
        }

        return null;
    }
}
