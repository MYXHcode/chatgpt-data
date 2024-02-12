package com.myxh.chatgpt.data.domain.openai.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MYXH
 * @date 2024/2/12
 * @description 账户状态
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Getter
@AllArgsConstructor
public enum UserAccountStatusVO
{
    AVAILABLE(0, "可用"),
    FREEZE(1, "冻结"),
    ;

    private final Integer code;
    private final String info;

    public static UserAccountStatusVO get(Integer code)
    {
        switch (code)
        {
            case 1:
                return UserAccountStatusVO.FREEZE;
            case 0:
            default:
                return UserAccountStatusVO.AVAILABLE;
        }
    }
}
