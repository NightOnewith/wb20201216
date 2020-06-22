package com.ethan.ryds.common.utils;

import com.ethan.ryds.common.constant.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * JWT工具类
 *
 * Created by ASUS on 2020/3/19.
 */
public class JWTUtils {

    //  服务器的key，用于做加解密的key数据
    private static final String JWT_SECRET = Constants.JwtSecret.JWT_SECRET.getValue();
    //  java对象和json的双向转换
    private static final ObjectMapper MAPPER = new ObjectMapper();
    //  请求成功状态码
    private static final int INTERNAL_SERVER_SUCCESS = Constants.HttpStatus.INTERNAL_SERVER_SUCCESS.getStatus();
    //  token过期
    public static final int JWT_ERRCODE_EXPIRE = Constants.Jwt.JWT_ERRCODE_EXPIRE.getValue();
    //  验证不通过
    public static final int JWT_ERRCODE_FAIL = Constants.Jwt.JWT_ERRCODE_FAIL.getValue();

    /**
     * 签发JWT，生成token。
     *
     * @param id            jwt的唯一身份标识，主要用作一次性token，从而回避重放攻击
     * @param iss           jwt签发者
     * @param subject      jwt所面向的用户，payload中记录的public claims
     * @param ttlMillis    有效期，单位毫秒
     * @return              token
     */
    public static String createToken(String id, String iss, String subject, long ttlMillis) {
        //  加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 自定义subject

        //  创建密钥
        String secretKey = generalKey();
        //  创建JWT的构建器，就是使用指定的信息和加密算法，生成token的工具。
        JwtBuilder builder = Jwts.builder()
                .setId(id)      //  设置身份标识，就是一个客户端的唯一标记，可以是用户主键、服务器生成的随机数等等。
                .setIssuer(iss)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, secretKey);   //  设定密钥和算法

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);    //   token过期时间
            String formatExpDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expDate);
            System.out.println("token过期时间：" + formatExpDate);

            Date startDate = new Date(nowMillis);    //   token过期时间
            String formatStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startDate);
            System.out.println("token开始时间：" + formatStartDate);

            // 设置token过期时间
            builder.setExpiration(expDate);
        }

        return builder.compact();   //  生成token
    }

    /**
     * 验证token
     * @param jwtStr  就是token
     * @return
     */
    public static JWTResult validateJWT(String jwtStr) {
        JWTResult checkResult = new JWTResult();
        Claims claims = null;

        try {
            claims = parseJWT(jwtStr);
            checkResult.setCode(INTERNAL_SERVER_SUCCESS);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } catch (ExpiredJwtException e) {
            checkResult.setCode(JWT_ERRCODE_EXPIRE);
            checkResult.setSuccess(false);
        } catch (SignatureException e) {
            checkResult.setCode(JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        } catch (Exception e) {
            checkResult.setCode(JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        }

        return checkResult;
    }

    /**
     * 解析token
     *
     * @param jwt  就是token
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        String secretKey = generalKey();

        //getBody()获取的就是token中记录的payload数据，就是payload中保存的所有claims,就是JWT的构建器中set的所有数据
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();

    }

    /**
     * 创建生成token所需的密钥(加密密钥)
     * @return
     */
    public static String generalKey() {
        byte[] textByte = new byte[0];
        try {
            textByte = JWT_SECRET.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encodedText = Base64.getEncoder().encodeToString(textByte);
        return encodedText;
    }

    /**
     * 生成subject信息（json格式的数据）-这里使用的是jackson，使用fastJson也都可以
     * @param subObj    要转换的对象，其实就是转换JWTSubject类对象
     * @return          java对象->json字符串出错时返回null
     */
    public static String generalSubject(Object subObj) {
        try {
            return MAPPER.writeValueAsString(subObj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private final static Base64.Encoder encoder = Base64.getEncoder();
    private final static Base64.Decoder decoder = Base64.getDecoder();

    /**
     * BASE64
     * 给字符串加密
     * @param text
     * @return
     */
    public static String BASE64_Encode(String text) {
        byte[] textByte = new byte[0];
        try {
            textByte = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encodedText = encoder.encodeToString(textByte);
        return encodedText;
    }

    /**
     * BASE64
     * 将加密后的字符串进行解密
     * @param encodedText
     * @return
     */
    public static String BASE64_Decode(String encodedText) {
        String text = null;
        try {
            text = new String(decoder.decode(encodedText), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }
}
