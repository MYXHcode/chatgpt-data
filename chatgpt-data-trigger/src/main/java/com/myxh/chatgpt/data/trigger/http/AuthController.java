package com.myxh.chatgpt.data.trigger.http;

import com.alibaba.fastjson.JSON;
import com.myxh.chatgpt.data.domain.auth.model.entity.AuthStateEntity;
import com.myxh.chatgpt.data.domain.auth.model.valobj.AuthTypeVO;
import com.myxh.chatgpt.data.domain.auth.service.IAuthService;
import com.myxh.chatgpt.data.domain.weixin.model.entity.MessageTextEntity;
import com.myxh.chatgpt.data.domain.weixin.model.entity.UserBehaviorMessageEntity;
import com.myxh.chatgpt.data.domain.weixin.model.valobj.MsgTypeVO;
import com.myxh.chatgpt.data.domain.weixin.service.IWeiXinBehaviorService;
import com.myxh.chatgpt.data.types.common.Constants;
import com.myxh.chatgpt.data.types.model.Response;
import com.myxh.chatgpt.data.types.sdk.weixin.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author MYXH
 * @date 2024/1/30
 * @description 鉴权登录
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/auth/")
public class AuthController
{
    @Resource
    private IAuthService authService;

    @Resource
    private IWeiXinBehaviorService weiXinBehaviorService;

    /**
     * 生成验证码，用于测试使用
     * <p>
     * curl -X POST \
     * http://myxh-chatqpt.nat300.top/api/v1/auth/gen/code \
     * -H 'Content-Type: application/x-www-form-urlencoded' \
     * -d 'openid=o0G6z6h-nHpZFUZVrcPJayOdN884'
     * <p>
     * curl -X POST \
     * http://localhost:8090/api/v1/auth/gen/code \
     * -H 'Content-Type: application/x-www-form-urlencoded' \
     * -d 'openid=o0G6z6h-nHpZFUZVrcPJayOdN884'
     */
    @RequestMapping(value = "gen/code", method = RequestMethod.POST)
    public Response<String> genCode(@RequestParam String openid)
    {
        log.info("生成验证码开始，用户ID: {}", openid);

        try
        {
            UserBehaviorMessageEntity userBehaviorMessageEntity = new UserBehaviorMessageEntity();
            userBehaviorMessageEntity.setOpenId(openid);
            userBehaviorMessageEntity.setMsgType(MsgTypeVO.TEXT.getCode());
            userBehaviorMessageEntity.setContent("405");
            String xml = weiXinBehaviorService.acceptUserBehavior(userBehaviorMessageEntity);
            MessageTextEntity messageTextEntity = XmlUtil.xmlToBean(xml, MessageTextEntity.class);
            log.info("生成验证码完成，用户ID: {} 生成结果：{}", openid, messageTextEntity.getContent());

            return Response.<String>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(messageTextEntity.getContent())
                    .build();
        }
        catch (Exception e)
        {
            log.info("生成验证码失败，用户ID: {}", openid);

            return Response.<String>builder()
                    .code(Constants.ResponseCode.TOKEN_ERROR.getCode())
                    .info(Constants.ResponseCode.TOKEN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 【myxh-chatqpt.nat300.top 是我在 <a href="https://natapp.cn/">https://natapp.cn</a> 购买的渠道，你需要自己购买一个使用】
     * 鉴权，根据鉴权结果返回 Token 码
     * curl -X POST \
     * http://myxh-chatqpt.nat300.top/api/v1/auth/login \
     * -H 'Content-Type: application/x-www-form-urlencoded' \
     * -d 'code=7397'
     * <p>
     * curl -X POST \
     * http://localhost:8090/api/v1/auth/login \
     * -H 'Content-Type: application/x-www-form-urlencoded' \
     * -d 'code=8815'
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Response<String> doLogin(@RequestParam String code)
    {
        log.info("鉴权登录校验开始，验证码: {}", code);

        try
        {
            AuthStateEntity authStateEntity = authService.doLogin(code);
            log.info("鉴权登录校验完成，验证码: {} 结果: {}", code, JSON.toJSONString(authStateEntity));

            // 拦截，鉴权失败
            if (!AuthTypeVO.A0000.getCode().equals(authStateEntity.getCode()))
            {
                return Response.<String>builder()
                        .code(Constants.ResponseCode.TOKEN_ERROR.getCode())
                        .info(Constants.ResponseCode.TOKEN_ERROR.getInfo())
                        .build();
            }

            // 放行，鉴权成功
            return Response.<String>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(authStateEntity.getToken())
                    .build();

        }
        catch (Exception e)
        {
            log.error("鉴权登录校验失败，验证码: {}", code);

            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
}
