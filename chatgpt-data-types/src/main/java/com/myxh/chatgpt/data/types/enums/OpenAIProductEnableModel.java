package com.myxh.chatgpt.data.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author MYXH
 * @date 2024/2/18
 * @description OpenAIProductEnableModel OpenAI 产品赋能模型
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Getter
@AllArgsConstructor
public enum OpenAIProductEnableModel
{
    CLOSE(0, "无效，已关闭"),
    OPEN(1, "有效，使用中"),
    ;

    private final Integer code;

    private final String info;

    public static OpenAIProductEnableModel get(Integer code)
    {
        switch (code)
        {
            case 1:
                return OpenAIProductEnableModel.OPEN;
            case 0:
            default:
                return OpenAIProductEnableModel.CLOSE;
        }
    }
}
