package com.my_spring_boot.wechat;

import lombok.Data;

/**
 * @author JiHC
 * @date 2020/8/19
 */
@Data
public class AccessToken {

  private String access_token;

  private Long expires_in;
}
