package com.myxh.chatgpt.data.domain.order.model.entity;

import com.myxh.chatgpt.data.domain.order.model.valobj.PayStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author MYXH
 * @date 2024/2/18
 * @description 未支付的有效订单实体
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnpaidOrderEntity
{
    /**
     * 用户 ID：微信分配的唯一 ID 编码
     */
    private String openid;

    /**
     * 订单 ID
     */
    private String orderId;

    /**
     * 订单金额
     */
    private BigDecimal totalAmount;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 支付地址：创建支付后，获得的URL地址
     */

    private String payUrl;
    /**
     * 支付状态：0-等待支付、1-支付完成、2-支付失败、3-放弃支付
     */
    private PayStatusVO payStatus;
}
