package com.myxh.chatgpt.data.domain.order.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MYXH
 * @date 2024/2/18
 * @description 支付状态
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Getter
@AllArgsConstructor
public enum PayStatusVO
{
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付完成"),
    FAIL(2, "支付失败"),
    ABANDON(3, "放弃支付"),
    ;

    private final Integer code;
    private final String desc;

    public static PayStatusVO get(Integer code)
    {
        switch (code)
        {
            case 1:
                return PayStatusVO.SUCCESS;
            case 2:
                return PayStatusVO.FAIL;
            case 3:
                return PayStatusVO.ABANDON;
            case 0:
            default:
                return PayStatusVO.WAIT;
        }
    }
}
