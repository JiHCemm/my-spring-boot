package com.my_spring_boot.util;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 *
 * @author JiHC
 * @since 2020/8/1
 */
public class MD5Utils {

  private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

  /**
   * 对字符串进行MD5编码
   *
   * @param originString 原数据
   * @return 加密后的字符
   */
  public static String MD5(String originString) {
    if (originString != null) {
      try {
        //创建具有指定算法名称的信息摘要
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
        byte[] results = md5.digest(originString.getBytes());
        //将得到的字节数组变成字符串返回
        return byteArrayToHexString(results);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /**
   * 高效的bytesToHexString： 通过对一个只读数组hexArray进行读取来实现
   */
  private static String byteArrayToHexString(byte... bytes) {
    char[] hexChars = new char[bytes.length * 2];
    for (int i = 0; i < bytes.length; i++) {
      int v = bytes[i] & 0xFF;
      hexChars[i * 2] = hexArray[v >>> 4];
      hexChars[i * 2 + 1] = hexArray[v & 0x0F];
    }
    return new String(hexChars);
  }
}
