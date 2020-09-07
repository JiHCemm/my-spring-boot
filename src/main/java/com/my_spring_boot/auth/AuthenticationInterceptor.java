package com.my_spring_boot.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.my_spring_boot.rbac.pojo.Admin;
import com.my_spring_boot.rbac.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 拦截器去获取token并验证token
 *
 * @author JiHC
 * @since 2020/8/17
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private IAdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse, Object object) {
        String token = httpServletRequest.getHeader ("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod ();
        //检查是否有@passtoken注解，有则跳过认证
        if (method.isAnnotationPresent (PassToken.class)) {
            PassToken passToken = method.getAnnotation (PassToken.class);
            if (passToken.required ()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent (UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation (UserLoginToken.class);
            if (userLoginToken.required ()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException ("无token");
                }
                // 获取 token 中的 user id
                String adminId;
                try {
                    adminId = JWT.decode (token).getAudience ().get (0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException ("用户未授权");
                }
                Admin admin = adminService.getById (adminId);
                if (admin == null) {
                    throw new RuntimeException ("用户不存在");
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require (Algorithm.HMAC256 (admin.getPassword ())).build ();
                try {
                    jwtVerifier.verify (token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException ("用户未授权");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
