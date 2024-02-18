package com.myxh.chatgpt.data.domain.order.model.entity;

import com.myxh.chatgpt.data.types.enums.OpenAIProductEnableModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author MYXH
 * @date 2024/2/18
 * @description 商品实体对象
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity
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

    /**
     * 商品状态：0无效、1有效
     */
    private OpenAIProductEnableModel enable;

    /**
     * 是否有效：true = 有效，false = 无效
     */
    public boolean isAvailable()
    {
        return OpenAIProductEnableModel.OPEN.equals(enable);
    }
}
