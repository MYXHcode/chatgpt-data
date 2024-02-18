package com.myxh.chatgpt.data.types.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author MYXH
 * @date 2024/1/29
 * @description Constants 常量
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
public class Constants
{
    public final static String SPLIT = ",";

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public enum ResponseCode
    {
        SUCCESS("0000", "成功"),
        UN_ERROR("0001", "未知失败"),
        ILLEGAL_PARAMETER("0002", "非法参数"),
        TOKEN_ERROR("0003", "权限拦截"),
        ORDER_PRODUCT_ERR("OE001", "所购商品已下线，请重新选择下单商品"),
        ;

        private String code;
        private String info;
    }
}
