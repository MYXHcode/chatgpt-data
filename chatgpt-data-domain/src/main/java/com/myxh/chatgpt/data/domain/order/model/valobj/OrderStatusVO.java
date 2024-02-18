package com.myxh.chatgpt.data.domain.order.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MYXH
 * @date 2024/2/18
 * @description 订单状态
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Getter
@AllArgsConstructor
public enum OrderStatusVO
{
    CREATE(0, "创建完成"),
    WAIT(1, "等待发货"),
    COMPLETED(2, "发货完成"),
    CLOSE(3, "系统关单"),
    ;

    private final Integer code;
    private final String desc;

    public static OrderStatusVO get(Integer code)
    {
        switch (code)
        {
            case 1:
                return OrderStatusVO.WAIT;
            case 2:
                return OrderStatusVO.COMPLETED;
            case 3:
                return OrderStatusVO.CLOSE;
            case 0:
            default:
                return OrderStatusVO.CREATE;
        }
    }
}
