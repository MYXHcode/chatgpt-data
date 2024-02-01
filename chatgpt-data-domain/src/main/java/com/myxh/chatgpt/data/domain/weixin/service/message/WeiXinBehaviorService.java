package com.myxh.chatgpt.data.domain.weixin.service.message;

import com.google.common.cache.Cache;
import com.myxh.chatgpt.data.domain.weixin.model.entity.MessageTextEntity;
import com.myxh.chatgpt.data.domain.weixin.model.entity.UserBehaviorMessageEntity;
import com.myxh.chatgpt.data.domain.weixin.model.valobj.MsgTypeVO;
import com.myxh.chatgpt.data.domain.weixin.service.IWeiXinBehaviorService;
import com.myxh.chatgpt.data.types.exception.ChatGPTException;
import com.myxh.chatgpt.data.types.sdk.weixin.XmlUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author MYXH
 * @date 2024/1/30
 * @description 受理用户行为接口实现类
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Service
public class WeiXinBehaviorService implements IWeiXinBehaviorService
{
    @Value("${wx.config.originalid}")
    private String originalId;

    @Resource
    private Cache<String, String> codeCache;

    /**
     * 1. 用户的请求行文，分为事件 event、消息 text，这里我们只处理消息内容
     * 2. 用户行为、消息类型，是多样性的，这部分如果用户有更多的扩展需求，可以使用设计模式【模板模式 + 策略模式 + 工厂模式】，分拆逻辑
     */
    @Override
    public String acceptUserBehavior(UserBehaviorMessageEntity userBehaviorMessageEntity)
    {
        // Event 事件类型，忽略处理
        if (MsgTypeVO.EVENT.getCode().equals(userBehaviorMessageEntity.getMsgType()))
        {
            return "";
        }

        // Text 文本类型
        if (MsgTypeVO.TEXT.getCode().equals(userBehaviorMessageEntity.getMsgType()))
        {
            // 缓存验证码
            String isExistCode = codeCache.getIfPresent(userBehaviorMessageEntity.getOpenId());

            // 判断验证码，不考虑验证码重复问题
            if (StringUtils.isBlank(isExistCode))
            {
                // 创建验证码
                String code = RandomStringUtils.randomNumeric(4);
                codeCache.put(code, userBehaviorMessageEntity.getOpenId());
                codeCache.put(userBehaviorMessageEntity.getOpenId(), code);
                isExistCode = code;
            }

            // 反馈信息[文本]
            MessageTextEntity res = new MessageTextEntity();
            res.setToUserName(userBehaviorMessageEntity.getOpenId());
            res.setFromUserName(originalId);
            res.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000L));
            res.setMsgType("text");
            res.setContent(String.format("您的验证码为：%s，有效期%d分钟！", isExistCode, 3));

            return XmlUtil.beanToXml(res);
        }

        throw new ChatGPTException(userBehaviorMessageEntity.getMsgType() + " 未被处理的行为类型 Err！");
    }
}
