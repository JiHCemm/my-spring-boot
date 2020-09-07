package com.my_spring_boot.util;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * @author JiHC
 * @date 2020/8/3
 */
@Service
@Lazy(false)
public class IdGenerator implements Serializable {
  private static SecureRandom random = new SecureRandom();

  /**
   * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
   */
  public static String uuid() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  /**
   * 使用SecureRandom随机生成Long.
   */
  public static long randomLong() {
    return Math.abs(random.nextLong());
  }
}
