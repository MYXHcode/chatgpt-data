package com.myxh.chatgpt.data.test;

import com.alibaba.fastjson2.JSON;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

/**
 * @author MYXH
 * @date 2024/2/18
 * @description 微信支付测试
 * <p>
 * 【支付渠道选择】
 * A；选择个体户申请；如天津网上申请；https://yct.scjg.tj.gov.cn/ywtb/#/layout/integration/index
 * B；选择第三方渠道；https://open.pay.yungouos.com/#/api/api/pay/wxpay/nativePay、https://www.ltzf.cn/doc - 大部分伙伴可以选择这个方式对接
 * <p>
 * 1. SDK APIv3 的官方，提供了各项配置的申请方式；https://github.com/wechatpay-apiv3/wechatpay-java
 * 2. 注册微信支付商户；https://pay.weixin.qq.com/index.php/apply/applyment_home/guide_normal - 你要有一个(个体户/公司)才可以申请，大部分城市都可以线上申请个体户
 * 3. 商户 API 证书：指由商户申请的，包含证书序列号、商户的商户号、公司名称、公钥信息的证书。https://pay.weixin.qq.com/docs/merchant/development/interface-rules/privatekey-and-certificate.html#%E5%95%86%E6%88%B7api%E8%AF%81%E4%B9%A6
 * 4. 商户 API 私钥：商户申请商户 API 证书时，会生成商户私钥，并保存在本地证书文件夹的文件 apiclient_key.pem 中。https://pay.weixin.qq.com/docs/merchant/development/interface-rules/privatekey-and-certificate.html#%E5%95%86%E6%88%B7api%E7%A7%81%E9%92%A5
 * 5. APIv3 密钥：为了保证安全性，微信支付在回调通知和平台证书下载接口中，对关键信息进行了 AES-256-GCM 加密。APIv3 密钥是加密时使用的对称密钥。https://pay.weixin.qq.com/docs/merchant/development/interface-rules/apiv3key.html
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Slf4j
public class WeChatPayTest
{
    // 公众号开发信息，开发者 ID(AppID) appId；https://mp.weixin.qq.com/advanced/advanced?action=dev&t=advanced/dev&token=329083210&lang=zh_CN
    private static final String appId = "";

    // 商户号：https://pay.weixin.qq.com/index.php/core/account/info - 打开可以看见微信支付商户号
    public static final String merchantId = "";

    // 商户 API 私钥路径；https://pay.weixin.qq.com/docs/merchant/development/interface-rules/privatekey-and-certificate.html
    public static final String privateKeyPath = System.getProperty("user.dir") + "/src/main/resources/cert/apiclient_key.pem";

    // 商户证书序列号【脚本：openssl x509 -in apiclient_cert.pem -noout -serial】
    public static final String merchantSerialNumber = "";

    // 商户APIV3密钥 32 位自己生成
    public static final String apiV3Key = "";

    private NativePayService nativePayService;

    @Before
    public void init()
    {
        Config config = new RSAAutoCertificateConfig.Builder()
                .merchantId(merchantId)
                .privateKeyFromPath(privateKeyPath)
                .merchantSerialNumber(merchantSerialNumber)
                .apiV3Key(apiV3Key)
                .build();

        this.nativePayService = new NativePayService.Builder().config(config).build();
    }

    @Test
    public void testPrepay()
    {
        PrepayRequest request = new PrepayRequest();

        // 支付金额，单位分
        Amount amount = new Amount();
        amount.setTotal(1);
        request.setAmount(amount);
        request.setAppid(appId);
        request.setMchid(merchantId);
        request.setDescription("测试商品标题");
        request.setNotifyUrl("https://api.myxh-chatqpt.site");
        request.setOutTradeNo("100000010002");

        // 调用下单方法，得到应答
        PrepayResponse response = nativePayService.prepay(request);

        // 获得支付URL，复制到 https://cli.im/ 生成二维码，手机扫码支付
        System.out.println(response.getCodeUrl());
    }

    @Test
    public void testQueryOrderById()
    {
        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(merchantId);
        request.setOutTradeNo("100000010001");
        Transaction transaction = nativePayService.queryOrderByOutTradeNo(request);
        log.info("测试结果：{}", JSON.toJSONString(transaction));
    }

    @Test
    public void testCloseOrder()
    {
        CloseOrderRequest request = new CloseOrderRequest();
        request.setMchid(merchantId);
        request.setOutTradeNo("100000010001");
        nativePayService.closeOrder(request);
    }
}
