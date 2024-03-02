package com.myxh.chatgpt.data.trigger.job;

import com.google.common.eventbus.EventBus;
import com.myxh.chatgpt.data.domain.order.service.IOrderService;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.QueryOrderByOutTradeNoRequest;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author MYXH
 * @date 2024/2/18
 * @description 检测未接收到或未正确处理的支付回调通知
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Slf4j
@Component()
public class NoPayNotifyOrderJob
{
    @Resource
    private IOrderService orderService;

    @Autowired(required = false)
    private NativePayService payService;

    @Resource
    private EventBus eventBus;

    @Value("${wxpay.config.mchid}")
    private String mchid;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    @Timed(value = "no_pay_notify_order_job", description = "定时任务，订单支付状态更新")
    @Scheduled(cron = "0 0/1 * * * ?")
    public void exec()
    {
        try
        {
            if (null == payService)
            {
                log.info("定时任务，订单支付状态更新。应用未配置支付渠道，任务不执行。");

                return;
            }

            List<String> orderIds = orderService.queryNoPayNotifyOrder();

            if (orderIds.isEmpty())
            {
                log.info("定时任务，订单支付状态更新，暂无未更新订单 orderId is null");

                return;
            }
            for (String orderId : orderIds)
            {
                // 查询结果
                QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
                request.setMchid(mchid);
                request.setOutTradeNo(orderId);
                Transaction transaction = payService.queryOrderByOutTradeNo(request);

                if (!Transaction.TradeStateEnum.SUCCESS.equals(transaction.getTradeState()))
                {
                    log.info("定时任务，订单支付状态更新，当前订单未支付 orderId is {}", orderId);
                    continue;
                }

                // 支付单号
                String transactionId = transaction.getTransactionId();
                Integer total = transaction.getAmount().getTotal();
                BigDecimal totalAmount = new BigDecimal(total).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                String successTime = transaction.getSuccessTime();

                // 更新订单
                boolean isSuccess = orderService.changeOrderPaySuccess(orderId, transactionId, totalAmount, dateFormat.parse(successTime));

                if (isSuccess)
                {
                    // 发布消息
                    eventBus.post(orderId);
                }
            }
        }
        catch (Exception e)
        {
            log.error("定时任务，订单支付状态更新失败", e);
        }
    }
}
