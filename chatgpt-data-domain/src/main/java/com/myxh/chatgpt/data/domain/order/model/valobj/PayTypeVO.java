package com.myxh.chatgpt.data.domain.order.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MYXH
 * @date 2024/2/18
 * @description 支付类型
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Getter
@AllArgsConstructor
public enum PayTypeVO
{
    WEIXIN_NATIVE(0, "微信 Native 支付"),
    ;

    private final Integer code;
    private final String desc;

    public static PayTypeVO get(Integer code)
    {
        switch (code)
        {
            case 0:
            default:
                return PayTypeVO.WEIXIN_NATIVE;
        }
    }
}
