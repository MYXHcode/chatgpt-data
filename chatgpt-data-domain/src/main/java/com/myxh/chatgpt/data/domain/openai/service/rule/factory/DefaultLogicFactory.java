package com.myxh.chatgpt.data.domain.openai.service.rule.factory;

import com.myxh.chatgpt.data.domain.openai.annotation.LogicStrategy;
import com.myxh.chatgpt.data.domain.openai.service.rule.ILogicFilter;
import lombok.Getter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MYXH
 * @date 2024/2/3
 * @description 规则工厂
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Service
public class DefaultLogicFactory
{
    public Map<String, ILogicFilter> logicFilterMap = new ConcurrentHashMap<>();

    public DefaultLogicFactory(List<ILogicFilter> logicFilters)
    {
        logicFilters.forEach(logic -> {
            LogicStrategy strategy = AnnotationUtils.findAnnotation(logic.getClass(), LogicStrategy.class);

            if (null != strategy)
            {
                logicFilterMap.put(strategy.logicMode().getCode(), logic);
            }
        });
    }

    public Map<String, ILogicFilter> openLogicFilter()
    {
        return logicFilterMap;
    }


    /**
     * 规则逻辑枚举
     */
    @Getter
    public enum LogicModel
    {
        ACCESS_LIMIT("ACCESS_LIMIT", "访问次数过滤"),
        SENSITIVE_WORD("SENSITIVE_WORD", "敏感词过滤"),
        ;

        private String code;
        private String info;

        LogicModel(String code, String info)
        {
            this.code = code;
            this.info = info;
        }

        public void setCode(String code)
        {
            this.code = code;
        }

        public void setInfo(String info)
        {
            this.info = info;
        }
    }
}
