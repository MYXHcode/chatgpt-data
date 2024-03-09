package com.myxh.chatgpt.data.trigger.http;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.EventBus;
import com.myxh.chatgpt.data.domain.auth.service.IAuthService;
import com.myxh.chatgpt.data.domain.order.model.entity.PayOrderEntity;
import com.myxh.chatgpt.data.domain.order.model.entity.ProductEntity;
import com.myxh.chatgpt.data.domain.order.model.entity.ShopCartEntity;
import com.myxh.chatgpt.data.domain.order.service.IOrderService;
import com.myxh.chatgpt.data.trigger.http.dto.SaleProductDTO;
import com.myxh.chatgpt.data.types.common.Constants;
import com.myxh.chatgpt.data.types.model.Response;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.service.partnerpayments.nativepay.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author MYXH
 * @date 2024/2/18
 * @description 售卖服务
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/sale/")
public class SaleController
{
    @Autowired(required = false)
    private NotificationParser notificationParser;

    @Resource
    private IOrderService orderService;

    @Resource
    private IAuthService authService;

    @Resource
    private EventBus eventBus;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    /**
     * 商品列表查询
     * 开始地址：http://localhost:8090/api/v1/sale/query_product_list
     * 测试地址：http://myxh-chatqpt.nat300.top/api/v1/sale/query_product_list
     * <p>
     * curl -X GET \
     * -H "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvMEc2ejZoLW5IcFpGVVpWcmNQSmF5T2ROODg0Iiwib3BlbklkIjoibzBHNno2aC1uSHBaRlVaVnJjUEpheU9kTjg4NCIsImV4cCI6MTcwODM0ODYwOSwiaWF0IjoxNzA3NzQzODA5LCJqdGkiOiI3NjM5ZGQxOC1lNzI3LTRmZTYtODU4ZC02NDAyMTY5ZjFmYWIifQ.jzmO_aYmnTCkzeottEXNknUItUL6Qv2mD9LOaccuaPo" \
     * -H "Content-Type: application/x-www-form-urlencoded" \
     * http://localhost:8090/api/v1/sale/query_product_list
     */
    @RequestMapping(value = "query_product_list", method = RequestMethod.GET)
    public Response<List<SaleProductDTO>> queryProductList(@RequestHeader("Authorization") String token)
    {
        try
        {
            // 1. Token 校验
            boolean success = authService.checkToken(token);

            if (!success)
            {
                return Response.<List<SaleProductDTO>>builder()
                        .code(Constants.ResponseCode.TOKEN_ERROR.getCode())
                        .info(Constants.ResponseCode.TOKEN_ERROR.getInfo())
                        .build();
            }

            // 2. 查询商品
            List<ProductEntity> productEntityList = orderService.queryProductList();
            log.info("商品查询 {}", JSON.toJSONString(productEntityList));

            List<SaleProductDTO> mallProductDTOS = new ArrayList<>();

            for (ProductEntity productEntity : productEntityList)
            {
                SaleProductDTO mallProductDTO = SaleProductDTO.builder()
                        .productId(productEntity.getProductId())
                        .productName(productEntity.getProductName())
                        .productDesc(productEntity.getProductDesc())
                        .price(productEntity.getPrice())
                        .quota(productEntity.getQuota())
                        .build();
                mallProductDTOS.add(mallProductDTO);
            }

            // 3. 返回结果
            return Response.<List<SaleProductDTO>>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(mallProductDTOS)
                    .build();
        }
        catch (Exception e)
        {
            log.error("商品查询失败", e);

            return Response.<List<SaleProductDTO>>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 用户商品下单
     * 开始地址：http://localhost:8090/api/v1/sale/create_pay_order?productId=
     * 测试地址：http://myxh-chatqpt.nat300.top/api/v1/sale/create_pay_order
     * <p>
     * curl -X POST \
     * -H "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvMEc2ejZoLW5IcFpGVVpWcmNQSmF5T2ROODg0Iiwib3BlbklkIjoibzBHNno2aC1uSHBaRlVaVnJjUEpheU9kTjg4NCIsImV4cCI6MTcwODM0ODYwOSwiaWF0IjoxNzA3NzQzODA5LCJqdGkiOiI3NjM5ZGQxOC1lNzI3LTRmZTYtODU4ZC02NDAyMTY5ZjFmYWIifQ.jzmO_aYmnTCkzeottEXNknUItUL6Qv2mD9LOaccuaPo" \
     * -H "Content-Type: application/x-www-form-urlencoded" \
     * -d "productId=1001" \
     * http://localhost:8090/api/v1/sale/create_pay_order
     */
    @RequestMapping(value = "create_pay_order", method = RequestMethod.POST)
    public Response<String> createParOrder(@RequestHeader("Authorization") String token, @RequestParam Integer productId)
    {
        try
        {
            // 1. Token 校验
            boolean success = authService.checkToken(token);
            if (!success)
            {
                return Response.<String>builder()
                        .code(Constants.ResponseCode.TOKEN_ERROR.getCode())
                        .info(Constants.ResponseCode.TOKEN_ERROR.getInfo())
                        .build();
            }

            // 2. Token 解析
            String openid = authService.openid(token);
            assert null != openid;
            log.info("用户商品下单，根据商品ID创建支付单开始 openid:{} productId:{}", openid, productId);

            ShopCartEntity shopCartEntity = ShopCartEntity.builder()
                    .openid(openid)
                    .productId(productId).build();

            PayOrderEntity payOrder = orderService.createOrder(shopCartEntity);
            log.info("用户商品下单，根据商品ID创建支付单完成 openid: {} productId: {} orderPay: {}", openid, productId, payOrder.toString());

            return Response.<String>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(payOrder.getPayUrl())
                    .build();
        }
        catch (Exception e)
        {
            log.error("用户商品下单，根据商品ID创建支付单失败", e);

            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 支付回调
     * 开发地址：http:/localhost:8090/api/v1/sale/pay_notify
     * 测试地址：http://myxh-chatqpt.nat300.top/api/v1/sale/pay_notify
     * 线上地址：https://api.myxh-chatqpt.site/api/v1/sale/pay_notify
     */
    @PostMapping("pay_notify")
    public void payNotify(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        try
        {
            // 随机串
            String nonceStr = request.getHeader("Wechatpay-Nonce");

            // 微信传递过来的签名
            String signature = request.getHeader("Wechatpay-Signature");

            // 证书序列号（微信平台）
            String serialNo = request.getHeader("Wechatpay-Serial");

            // 时间戳
            String timestamp = request.getHeader("Wechatpay-Timestamp");

            // 构造 RequestParam
            com.wechat.pay.java.core.notification.RequestParam requestParam = new com.wechat.pay.java.core.notification.RequestParam.Builder()
                    .serialNumber(serialNo)
                    .nonce(nonceStr)
                    .signature(signature)
                    .timestamp(timestamp)
                    .body(requestBody)
                    .build();

            // 以支付通知回调为例，验签、解密并转换成 Transaction
            Transaction transaction = notificationParser.parse(requestParam, Transaction.class);

            Transaction.TradeStateEnum tradeState = transaction.getTradeState();

            if (Transaction.TradeStateEnum.SUCCESS.equals(tradeState))
            {
                // 支付单号
                String orderId = transaction.getOutTradeNo();
                String transactionId = transaction.getTransactionId();
                Integer total = transaction.getAmount().getTotal();
                String successTime = transaction.getSuccessTime();
                Date payTime = null;
                if (StringUtils.isBlank(successTime))
                {
                    payTime = new Date();
                }
                else
                {
                    payTime = dateFormat.parse(successTime);
                }

                log.info("支付成功 orderId:{} total:{} successTime: {}", orderId, total, successTime);

                // 更新订单
                boolean isSuccess = orderService.changeOrderPaySuccess(orderId, transactionId, new BigDecimal(total).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP), payTime);

                if (isSuccess)
                {
                    // 发布消息
                    eventBus.post(orderId);
                }

                response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
            }
            else
            {
                response.getWriter().write("<xml><return_code><![CDATA[FAIL]]></return_code></xml>");
            }
        }
        catch (Exception e)
        {
            log.error("支付失败", e);
            response.getWriter().write("<xml><return_code><![CDATA[FAIL]]></return_code></xml>");
        }
    }
}
