package com.myxh.chatgpt.data.domain.openai.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MYXH
 * @date 2024/2/3
 * @description 逻辑校验类型：值对象
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Getter
@AllArgsConstructor
public enum LogicCheckTypeVO
{
    SUCCESS("0000", "校验通过"),
    REFUSE("0001", "校验拒绝"),
    ;

    private final String code;
    private final String info;
}
