package com.myxh.chatgpt.data.trigger.job;

import com.myxh.chatgpt.data.domain.order.service.IOrderService;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.CloseOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author MYXH
 * @date 2024/2/18
 * @description 超时关单任务
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Slf4j
@Component()
public class TimeoutCloseOrderJob
{
    @Resource
    private IOrderService orderService;

    @Autowired(required = false)
    private NativePayService payService;

    @Value("${wxpay.config.mchid}")
    private String mchid;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void exec()
    {
        try
        {
            if (null == payService)
            {
                log.info("定时任务，订单支付状态更新。应用未配置支付渠道，任务不执行。");

                return;
            }

            List<String> orderIds = orderService.queryTimeoutCloseOrderList();

            if (orderIds.isEmpty())
            {
                log.info("定时任务，超时30分钟订单关闭，暂无超时未支付订单 orderIds is null");

                return;
            }

            for (String orderId : orderIds)
            {
                boolean status = orderService.changeOrderClose(orderId);

                //微信关单：暂时不需要主动关闭
                CloseOrderRequest request = new CloseOrderRequest();
                request.setMchid(mchid);
                request.setOutTradeNo(orderId);
                payService.closeOrder(request);

                log.info("定时任务，超时30分钟订单关闭 orderId: {} status：{}", orderId, status);
            }
        }
        catch (Exception e)
        {
            log.error("定时任务，超时15分钟订单关闭失败", e);
        }
    }
}
