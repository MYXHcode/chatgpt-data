package com.myxh.chatgpt.data.infrastructure.dao;

import com.myxh.chatgpt.data.infrastructure.po.UserAccountPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author MYXH
 * @date 2024/2/12
 * @description 用户账户 DAO
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Mapper
public interface IUserAccountDao
{
    int subAccountQuota(String openid);

    UserAccountPO queryUserAccount(String openid);

    void insert(UserAccountPO userAccountPOReq);

    int addAccountQuota(UserAccountPO userAccountPOReq);
}
