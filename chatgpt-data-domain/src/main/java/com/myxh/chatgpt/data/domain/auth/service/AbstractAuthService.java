package com.myxh.chatgpt.data.domain.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.myxh.chatgpt.data.domain.auth.model.entity.AuthStateEntity;
import com.myxh.chatgpt.data.domain.auth.model.valobj.AuthTypeVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author MYXH
 * @date 2024/1/30
 * @description AbstractAuthService 抽象身份验证服务
 * @GitHub <a href="https://github.com/MYXHcode">MYXHcode</a>
 */
@Slf4j
public abstract class AbstractAuthService implements IAuthService
{
    /**
     * SecretKey 要替换为你自己的，并且最好是通过配置的方式使用
     */
    private static final String defaultBase64EncodedSecretKey = "MYXH";
    private final String base64EncodedSecretKey = Base64.encodeBase64String(defaultBase64EncodedSecretKey.getBytes());
    private final Algorithm algorithm = Algorithm.HMAC256(Base64.decodeBase64(Base64.encodeBase64String(defaultBase64EncodedSecretKey.getBytes())));

    @Override
    public AuthStateEntity doLogin(String code)
    {
        // 1. 如果不是 4 位到 8 位有效数字字符串，则返回验证码无效
        if (!code.matches("\\d{4,8}"))
        {
            log.info("鉴权，用户收入的验证码无效 {}", code);

            return AuthStateEntity.builder()
                    .code(AuthTypeVO.A0002.getCode())
                    .info(AuthTypeVO.A0002.getInfo())
                    .build();
        }

        // 2. 校验判断，非成功则直接返回
        AuthStateEntity authStateEntity = this.checkCode(code);

        if (!authStateEntity.getCode().equals(AuthTypeVO.A0000.getCode()))
        {
            return authStateEntity;
        }

        // 3. 获取 Token 并返回
        Map<String, Object> chaim = new HashMap<>();
        chaim.put("openId", authStateEntity.getOpenId());
        String token = encode(authStateEntity.getOpenId(), 7 * 24 * 60 * 60 * 1000, chaim);
        authStateEntity.setToken(token);

        return authStateEntity;
    }

    protected abstract AuthStateEntity checkCode(String code);

    /**
     * 这里就是产生 JWT 字符串的地方
     * JWT 字符串包括三个部分
     * 1. header
     * - 当前字符串的类型，一般都是“JWT”
     * - 哪种算法加密，“HS256”或者其他的加密算法
     * 所以一般都是固定的，没有什么变化
     * 2. payload
     * 一般有四个最常见的标准字段（下面有）
     * iat：签发时间，也就是这个 JWT 什么时候生成的
     * jti：JWT 的唯一标识
     * iss：签发人，一般都是 username 或者 userId
     * exp：过期时间
     */
    protected String encode(String issuer, long ttlMillis, Map<String, Object> claims)
    {
        // iss签发人，ttlMillis 生存时间，claims 是指还想要在 JWT 中存储的一些非隐私信息
        if (claims == null)
        {
            claims = new HashMap<>();
        }

        long nowMillis = System.currentTimeMillis();

        JwtBuilder builder = Jwts.builder()
                // 荷载部分
                .setClaims(claims)
                // 这个是 JWT 的唯一标识，一般设置成唯一的，这个方法可以生成唯一标识
                .setId(UUID.randomUUID().toString())
                // 签发时间
                .setIssuedAt(new Date(nowMillis))
                // 签发人，也就是 JWT 是给谁的（逻辑上一般都是 username 或者 userId）
                .setSubject(issuer)
                // 这个地方是生成 JWT 使用的算法和秘钥
                .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey);

        if (ttlMillis >= 0)
        {
            long expMillis = nowMillis + ttlMillis;

            // 4. 过期时间，这个也是使用毫秒生成的，使用当前时间+前面传入的持续时间生成
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }


    /**
     * 相当于 encode 的方向，传入 jwtToken 生成对应的 username 和 password 等字段。Claim 就是一个 map，也就是拿到荷载部分所有的键值对
     *
     * @param jwtToken JWT 令牌
     * @return Claims 声称
     */
    protected Claims decode(String jwtToken)
    {
        // 得到 DefaultJwtParser
        return Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(base64EncodedSecretKey)
                // 设置需要解析的 JWT
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    /**
     * 判断jwtToken是否合法
     *
     * @param jwtToken JWT 令牌
     * @return boolean 布尔值
     */
    protected boolean isVerify(String jwtToken)
    {
        try
        {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(jwtToken);

            // 校验不通过会抛出异常
            // 判断合法的标准：1. 头部和荷载部分没有篡改过 2. 没有过期
            return true;
        }
        catch (Exception e)
        {
            log.error("jwt isVerify Err", e);

            return false;
        }
    }
}
