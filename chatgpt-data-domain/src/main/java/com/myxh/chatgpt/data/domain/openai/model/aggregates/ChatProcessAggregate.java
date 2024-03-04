package com.myxh.chatgpt.data.domain.openai.model.aggregates;

import com.myxh.chatgpt.data.domain.openai.model.entity.MessageEntity;
import com.myxh.chatgpt.data.domain.openai.model.valobj.GenerativeModelVO;
import com.myxh.chatgpt.data.types.common.Constants;
import com.myxh.chatgpt.data.types.enums.ChatGPTModel;
import com.myxh.chatgpt.data.types.enums.OpenAiChannel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author MYXH
 * @date 2024/1/29
 * @description ChatProcessAggregate Chat 过程聚合
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatProcessAggregate
{
    /**
     * 用户 ID
     */
    private String openid;

    /**
     * 验证信息
     */
    private String token;

    /**
     * 默认模型
     */
    private String model = ChatGPTModel.GPT_3_5_TURBO.getCode();

    /**
     * 问题描述
     */
    private List<MessageEntity> messages;

    public boolean isWhiteList(String whiteListStr)
    {
        String[] whiteList = whiteListStr.split(Constants.SPLIT);

        for (String whiteOpenid : whiteList)
        {
            if (whiteOpenid.equals(openid))
            {
                return true;
            }
        }

        return false;
    }

    public OpenAiChannel getChannel()
    {
        return OpenAiChannel.getChannel(this.model);
    }

    public GenerativeModelVO getGenerativeModelVO()
    {
        switch (this.model)
        {
            case "dall-e-2":
            case "dall-e-3":
                return GenerativeModelVO.IMAGES;
            default:
                return GenerativeModelVO.TEXT;
        }
    }
}
