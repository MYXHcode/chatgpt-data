package com.myxh.chatgpt.data.domain.openai.model.entity;

import com.myxh.chatgpt.data.domain.openai.model.valobj.LogicCheckTypeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MYXH
 * @date 2024/2/3
 * @description 规则校验结果实体
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleLogicEntity<T>
{
    private LogicCheckTypeVO type;
    private String info;
    private T data;
}
