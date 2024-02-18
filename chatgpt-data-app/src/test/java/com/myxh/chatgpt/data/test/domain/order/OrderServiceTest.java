package com.myxh.chatgpt.data.test.domain.order;

import com.myxh.chatgpt.data.domain.order.model.entity.PayOrderEntity;
import com.myxh.chatgpt.data.domain.order.model.entity.ShopCartEntity;
import com.myxh.chatgpt.data.domain.order.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author MYXH
 * @date 2024/2/18
 * @description 订单服务测试
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest
{
    @Resource
    private IOrderService orderService;

    @Test
    public void testCreateOrder()
    {
        ShopCartEntity shopCartEntity = ShopCartEntity.builder()
                .openid("o0G6z6h-nHpZFUZVrcPJayOdN884")
                .productId(1001)
                .build();
        PayOrderEntity payOrderEntity = orderService.createOrder(shopCartEntity);
        log.info("请求参数：{} 测试结果: {}", shopCartEntity, payOrderEntity);
    }
}
