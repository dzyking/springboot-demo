package com.demo.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.demo.constant.RedisConstant;
import com.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Component
public class TokenUtil {
    //设置过期时间
    //private static final long EXPIRE_DATE=2*60*60*1000;

    private static final String TOKEN_SECRET = "14734525866";
    private static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        TokenUtil.redisTemplate = redisTemplate;
    }

    //token秘钥
    public static String token(User user) {

        String token = "";
        try {
            //过期时间
            // Date date = new Date(System.currentTimeMillis()+EXPIRE_DATE);
            //秘钥及加密算法
            /*Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String,Object> header = new HashMap<>();
            header.put("typ","JWT");
            header.put("alg","HS256");
            //携带userId，phoneNum信息，生成签名
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("userId",user.getUserId())
                    .withClaim("phoneNum",user.getPhoneNum())//.withExpiresAt(date)
                    .sign(algorithm);*/
            token = CommonUtil.getUUID();
            //将签名及用户信息存入redis
            redisTemplate.opsForValue().set(RedisConstant.TOKEN_NAME + token, user, RedisConstant.TOKEN_TIME, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return token;
    }

    public static boolean verify(String token) {
        /**
         * @desc 验证token，通过返回true
         * @params [token]需要校验的串
         **/
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param token
     * @author: lrj
     * @Description:根据token获取用户信息,token即将过期，则刷新缓存
     * @Date: 2021/5/7
     * @return: com.xunda.im.dao.User
     */
    public static User getUserByToken(String token) {
        /*DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("userId").asString();*/
        String key = RedisConstant.TOKEN_NAME + token;
        Boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey != null && hasKey) {
            //即将过期，刷新缓存
            int seconds = redisTemplate.opsForValue().getOperations().getExpire(key).intValue();
            User user = (User) redisTemplate.opsForValue().get(key);
            if (seconds < 2 * 60 * 60 && seconds > 0) {
                redisTemplate.opsForValue().set(key, user, RedisConstant.TOKEN_TIME, TimeUnit.SECONDS);
            }
            return user;
        }
        return null;
    }

    /**
     * @param
     * @author: lrj
     * @Description: 获取用户信息
     * @Date: 2021/5/7
     * @return: com.xunda.im.dao.User
     */
    public static User getUser() {
        HttpServletRequest request = ServletUtil.getRequest();
        //获取头部header 信息
        String token = request.getHeader("Authorization");
        return getUserByToken(token);
    }

    /**
     * @param
     * @author: lrj
     * @Description:获取用户ID
     * @Date: 2021/5/7
     * @return: java.lang.String
     */
    public static Integer getUserId() {
        HttpServletRequest request = ServletUtil.getRequest();
        //获取头部header 信息
        String token = request.getHeader("Authorization");
        User user = getUserByToken(token);
        if (user != null) {
            return user.getId();
        }
        return null;
    }
}
