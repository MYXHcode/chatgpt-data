package com.myxh.chatgpt.data.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author MYXH
 * @date 2024/2/18
 * @description OpenAi 产品持久化对象
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenAIProductPO
{
    /**
     * 自增 ID
     */
    private Long id;

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

    /**
     * 商品排序
     */
    private Integer sort;

    /**
     * 是否有效：0无效、1有效
     */
    private Integer isEnabled;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
