package com.my_springboot.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.my_springboot.rbac.pojo.Admin;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 下发token
 *
 * @author JiHC
 * @since 2020/8/17
 */
@Service
public class TokenService {

    public String getToken(Admin admin) {
        Date start = new Date ();
        long currentTime = System.currentTimeMillis () + 60 * 60 * 1000;//一小时有效时间
        Date end = new Date (currentTime);
        return JWT.create ().withAudience (admin.getId ()).withIssuedAt (start)
                .withExpiresAt (end)
                .sign (Algorithm.HMAC256 (admin.getPassword ()));
    }
}
