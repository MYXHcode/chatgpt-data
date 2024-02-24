package com.myxh.chatgpt.data.domain.order.model.entity;

import com.myxh.chatgpt.data.domain.order.model.valobj.PayStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MYXH
 * @date 2024/2/18
 * @description 支付单实体
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayOrderEntity
{
    /**
     * 用户 ID
     */
    private String openid;

    /**
     * 订单 ID
     */
    private String orderId;

    /**
     * 支付地址：创建支付后，获得的 URL 地址
     */
    private String payUrl;

    /**
     * 支付状态：0-等待支付、1-支付完成、2-支付失败、3-放弃支付
     */
    private PayStatusVO payStatus;

    @Override
    public String toString()
    {
        return "PayOrderEntity{" +
                "openid='" + openid + '\'' +
                ", orderId='" + orderId + '\'' +
                ", payUrl='" + payUrl + '\'' +
                ", payStatus=" + payStatus.getCode() + ": " + payStatus.getDesc() +
                '}';
    }
}
