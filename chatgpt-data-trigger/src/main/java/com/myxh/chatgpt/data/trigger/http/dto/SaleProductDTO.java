package com.myxh.chatgpt.data.trigger.http.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author MYXH
 * @date 2024/2/18
 * @description 商品对象 DTO
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleProductDTO
{
    /**
     * 商品 ID
     */
    private Integer productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品描述
     */
    private String productDesc;

    /**
     * 额度次数
     */
    private Integer quota;

    /**
     * 商品价格
     */
    private BigDecimal price;
}
