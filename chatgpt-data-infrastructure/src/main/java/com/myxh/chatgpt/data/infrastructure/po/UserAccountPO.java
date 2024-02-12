package com.myxh.chatgpt.data.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author MYXH
 * @date 2024/2/12
 * @description 用户账户持久化对象
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountPO
{
    /**
     * 自增 ID
     */
    private Long id;

    /**
     * 用户 ID：这里用的是微信 ID 作为唯一 ID，你也可以给用户创建唯一 ID，之后绑定微信 ID
     */
    private String openid;

    /**
     * 总量额度
     */
    private Integer totalQuota;

    /**
     * 剩余额度
     */
    private Integer surplusQuota;

    /**
     * 可用模型：gpt-3.5-turbo, gpt-3.5-turbo-16k, gpt-4, gpt-4-32k
     */
    private String modelTypes;

    /**
     * 账户状态：0-可用、1-冻结
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
