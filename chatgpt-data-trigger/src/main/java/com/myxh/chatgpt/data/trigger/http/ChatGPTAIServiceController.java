package com.myxh.chatgpt.data.trigger.http;

import com.alibaba.fastjson.JSON;
import com.myxh.chatgpt.data.domain.auth.service.IAuthService;
import com.myxh.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import com.myxh.chatgpt.data.domain.openai.model.entity.MessageEntity;
import com.myxh.chatgpt.data.domain.openai.service.IChatService;
import com.myxh.chatgpt.data.trigger.http.dto.ChatGPTRequestDTO;
import com.myxh.chatgpt.data.types.common.Constants;
import com.myxh.chatgpt.data.types.exception.ChatGPTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author MYXH
 * @date 2024/1/29
 * @description ChatGPT AI 服务
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/chatgpt/")
public class ChatGPTAIServiceController
{
    @Resource
    private IChatService chatService;

    @Resource
    private IAuthService authService;

    /**
     * 【myxh-chatqpt.nat300.top 是我在 <a href="https://natapp.cn/">https://natapp.cn</a> 购买的渠道，你需要自己购买一个使用】
     * 流式问题，ChatGPT 请求接口
     * <p>
     * curl -X POST \
     * http://myxh-chatqpt.nat300.top/api/v1/chatgpt/chat/completions \
     * -H 'Content-Type: application/json;charset=utf-8' \
     * -H 'Authorization: MYXH' \
     * -d '{
     * "messages": [
     * {
     * "content": "写一个 java 冒泡排序",
     * "role": "user"
     * }
     * ],
     * "model": "gpt-3.5-turbo"
     * }'
     * <p>
     * curl -X POST \
     * http://localhost:8090/api/v1/chatgpt/chat/completions \
     * -H 'Content-Type: application/json;charset=utf-8' \
     * -H 'Authorization: MYXH' \
     * -d '{
     * "messages": [
     * {
     * "content": "写一个 java 冒泡排序",
     * "role": "user"
     * }
     * ],
     * "model": "gpt-3.5-turbo"
     * }'
     */
    @RequestMapping(value = "chat/completions", method = RequestMethod.POST)
    public ResponseBodyEmitter completionsStream(@RequestBody ChatGPTRequestDTO request, @RequestHeader("Authorization") String token, HttpServletResponse response)
    {
        log.info("流式问答请求开始，使用模型：{} 请求信息：{}", request.getModel(), JSON.toJSONString(request.getMessages()));

        try
        {
            // 1. 基础配置；流式输出、编码、禁用缓存
            response.setContentType("text/event-stream");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-cache");

            // 2. 构建异步响应对象【对 Token 过期拦截】
            ResponseBodyEmitter emitter = new ResponseBodyEmitter(3 * 60 * 1000L);
            boolean success = authService.checkToken(token);

            if (!success)
            {
                try
                {
                    emitter.send(Constants.ResponseCode.TOKEN_ERROR.getCode());
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }

                emitter.complete();

                return emitter;
            }

            // 3. 构建参数
            ChatProcessAggregate chatProcessAggregate = ChatProcessAggregate.builder()
                    .token(token)
                    .model(request.getModel())
                    .messages(request.getMessages().stream()
                            .map(entity -> MessageEntity.builder()
                                    .role(entity.getRole())
                                    .content(entity.getContent())
                                    .name(entity.getName())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();

            // 4. 请求结果&返回
            return chatService.completions(emitter, chatProcessAggregate);
        }
        catch (Exception e)
        {
            log.error("流式应答，请求模型：{} 发生异常", request.getModel(), e);
            throw new ChatGPTException(e.getMessage());
        }
    }
}
