package com.myxh.chatgpt.data.types.exception;

/**
 * @author MYXH
 * @date 2024/1/29
 * @description ChatGPTException ChatGPT 异常
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
public class ChatGPTException extends RuntimeException
{
    /**
     * 异常码
     */
    private final String code;

    /**
     * 异常信息
     */
    private String message;

    public ChatGPTException(String code)
    {
        this.code = code;
    }

    public ChatGPTException(String code, Throwable cause)
    {
        this.code = code;
        super.initCause(cause);
    }

    public ChatGPTException(String code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public ChatGPTException(String code, String message, Throwable cause)
    {
        this.code = code;
        this.message = message;
        super.initCause(cause);
    }
}
