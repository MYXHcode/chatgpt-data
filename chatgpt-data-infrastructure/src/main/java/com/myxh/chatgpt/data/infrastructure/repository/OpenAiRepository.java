package com.myxh.chatgpt.data.infrastructure.repository;

import com.myxh.chatgpt.data.domain.openai.model.entity.UserAccountQuotaEntity;
import com.myxh.chatgpt.data.domain.openai.model.valobj.UserAccountStatusVO;
import com.myxh.chatgpt.data.domain.openai.repository.IOpenAiRepository;
import com.myxh.chatgpt.data.infrastructure.dao.IUserAccountDao;
import com.myxh.chatgpt.data.infrastructure.po.UserAccountPO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author MYXH
 * @date 2024/2/12
 * @description OpenAi 仓储服务
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Repository
public class OpenAiRepository implements IOpenAiRepository
{
    @Resource
    private IUserAccountDao userAccountDao;

    @Override
    public int subAccountQuota(String openai)
    {
        return userAccountDao.subAccountQuota(openai);
    }

    @Override
    public UserAccountQuotaEntity queryUserAccount(String openid)
    {
        UserAccountPO userAccountPO = userAccountDao.queryUserAccount(openid);

        if (null == userAccountPO)
        {
            return null;
        }

        UserAccountQuotaEntity userAccountQuotaEntity = new UserAccountQuotaEntity();
        userAccountQuotaEntity.setOpenid(userAccountPO.getOpenid());
        userAccountQuotaEntity.setTotalQuota(userAccountPO.getTotalQuota());
        userAccountQuotaEntity.setSurplusQuota(userAccountPO.getSurplusQuota());
        userAccountQuotaEntity.setUserAccountStatusVO(UserAccountStatusVO.get(userAccountPO.getStatus()));
        userAccountQuotaEntity.genModelTypes(userAccountPO.getModelTypes());

        return userAccountQuotaEntity;
    }
}
