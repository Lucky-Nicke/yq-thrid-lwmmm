package com.lanxige.util;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtHelper {
    //token过期时间
    private static final long EXPIRE = 1000 * 60 * 60 * 24;
    //加密密钥
    private static final String APP_SECRET = "NSpSpmwd2Gmm62eoRxh4CifiwZxVv6sGjOzMF7VA8wqgopIg0c419RgAZcyDfWnPCAqpJmiWY7lEbeS3pCqWdaAkzcnr4UdXonUqGIobWKjCJXAEVamQYcBcFY5m25OtNSpSpmwd2Gmm62eoRxh4CifiwZxVv6sGjOzMF7VA8wqgopIg0c419RgAZcyDfWnPCAqpJmiWY7lEbeS3pCqWdaAkzcnr4UdXonUqGIobWKjCJXAEVamQYcBcFY5m25Ot";

    //根据用户id和用户名来生成token字符串
    public static String createToken(Long id, String username) {
        return Jwts.builder()
                .setSubject("gec-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("userId", id)
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
    }

    //从字符串获取用户id
    public static String getId(String token) {
        if(token.isEmpty()) {
            return null;
        }

        Jws<Claims> claims = Jwts.parser().setSigningKey(APP_SECRET).build().parseClaimsJws(token);
        return claims.getBody().get("userId").toString();
    }

    //从字符串获取用户名
    public static String getUsername(String token) {
        if(token.isEmpty()) {
            return null;
        }

        Jws<Claims> claims = Jwts.parser().setSigningKey(APP_SECRET).build().parseClaimsJws(token);
        return claims.getBody().get("username").toString();
    }

    //测试jwt
//    public static void main(String[] args) {
//        //生成jwt token
//        String token = JwtHelper.createToken("1", "admin");
//        System.out.println(token);
//
//        //解析token
//        String username = JwtHelper.getUsername(token);
//        System.out.println(username);
//
//        String id = JwtHelper.getId(token);
//        System.out.println(id);
//    }
}
