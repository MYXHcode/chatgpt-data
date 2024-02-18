package com.myxh.chatgpt.data.domain.order.model.aggregates;

import com.myxh.chatgpt.data.domain.order.model.entity.OrderEntity;
import com.myxh.chatgpt.data.domain.order.model.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MYXH
 * @date 2024/2/18
 * @description 下单聚合对象
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderAggregate
{
    /**
     * 用户 ID：微信用户唯一标识
     */
    private String openid;

    /**
     * 商品
     */
    private ProductEntity product;

    /**
     * 订单
     */
    private OrderEntity order;
}
