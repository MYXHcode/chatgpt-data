package com.myxh.chatgpt.data.infrastructure.dao;

import com.myxh.chatgpt.data.infrastructure.po.OpenAIOrderPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author MYXH
 * @date 2024/2/18
 * @description 订单 Dao
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Mapper
public interface IOpenAIOrderDao
{
    OpenAIOrderPO queryUnpaidOrder(OpenAIOrderPO openAIOrderPOReq);

    void insert(OpenAIOrderPO order);

    void updateOrderPayInfo(OpenAIOrderPO openAIOrderPO);

    int changeOrderPaySuccess(OpenAIOrderPO openAIOrderPO);

    OpenAIOrderPO queryOrder(String orderId);

    int updateOrderStatusDeliverGoods(String orderId);

    List<String> queryReplenishmentOrder();

    List<String> queryNoPayNotifyOrder();

    List<String> queryTimeoutCloseOrderList();

    boolean changeOrderClose(String orderId);
}
