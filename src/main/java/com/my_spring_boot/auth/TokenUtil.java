package com.my_spring_boot.auth;

import com.auth0.jwt.JWT;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * token工具类
 *
 * @author JiHC
 * @since 2020/8/22
 */
public class TokenUtil {
    public static String getTokenUserId() {
        if (null == getRequest ()) {
            return null;
        }
        String token = getRequest ().getHeader ("token");// 从 http 请求头中取出 token
        return JWT.decode (token).getAudience ().get (0);
    }

    /**
     * 获取request
     */
    private static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes ();
        return requestAttributes == null ? null : requestAttributes.getRequest ();
    }
}
