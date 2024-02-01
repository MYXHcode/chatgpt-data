package com.myxh.chatgpt.data.domain.auth.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author MYXH
 * @date 2024/1/30
 * @description AuthTypeVO 鉴权类型
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AuthTypeVO
{
    A0000("0000", "验证成功"),
    A0001("0001", "验证码不存在"),
    A0002("0002", "验证码无效");

    private String code;
    private String info;
}
