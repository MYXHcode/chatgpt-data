package com.myxh.chatgpt.data.domain.openai.annotation;

import com.myxh.chatgpt.data.domain.openai.service.rule.factory.DefaultLogicFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author MYXH
 * @date 2024/2/3
 * @description LogicStrategy 逻辑策略
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogicStrategy
{
    DefaultLogicFactory.LogicModel logicMode();
}

