package com.myxh.chatgpt.data.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MYXH
 * @date 2024/3/1
 * @description 模型对象
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Getter
@AllArgsConstructor
public enum ChatGLMModel
{
    CHATGLM_6B_SSE("chatGLM_6b_SSE"),
    CHATGLM_LITE("chatglm_lite"),
    CHATGLM_LITE_32K("chatglm_lite_32k"),
    CHATGLM_STD("chatglm_std"),
    CHATGLM_PRO("chatglm_pro"),
    CHATGLM_TURBO("chatglm_turbo")
    ;

    private final String code;

    public static ChatGLMModel get(String code)
    {
        switch (code)
        {
            case "chatglm_lite":
                return ChatGLMModel.CHATGLM_LITE;
            case "chatglm_lite_32k":
                return ChatGLMModel.CHATGLM_LITE_32K;
            case "chatglm_std":
                return ChatGLMModel.CHATGLM_STD;
            case "chatglm_pro":
                return ChatGLMModel.CHATGLM_PRO;
            case "chatglm_turbo":
                return ChatGLMModel.CHATGLM_TURBO;
            case "chatGLM_6b_SSE":
            default:
                return ChatGLMModel.CHATGLM_6B_SSE;
        }
    }
}
