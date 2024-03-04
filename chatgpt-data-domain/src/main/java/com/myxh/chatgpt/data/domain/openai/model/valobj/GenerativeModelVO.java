package com.myxh.chatgpt.data.domain.openai.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MYXH
 * @date 2024/3/4
 * @description 模型生成类型
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Getter
@AllArgsConstructor
public enum GenerativeModelVO
{
    TEXT("TEXT", "文本"),
    IMAGES("IMAGES", "图片"),
    ;

    private final String code;
    private final String info;
}
