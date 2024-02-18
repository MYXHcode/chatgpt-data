package com.myxh.chatgpt.data.infrastructure.dao;

import com.myxh.chatgpt.data.infrastructure.po.OpenAIProductPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author MYXH
 * @date 2024/2/18
 * @description 商品 Dao
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Mapper
public interface IOpenAIProductDao
{
    OpenAIProductPO queryProductByProductId(Integer productId);

    List<OpenAIProductPO> queryProductList();
}
