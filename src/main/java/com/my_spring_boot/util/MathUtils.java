package com.my_spring_boot.util;

/**
 * 生成位随机验证码工具类
 *
 * @author JiHC
 * @date 2020/8/4
 */
public class MathUtils {
  /**
   * 1.纯数字随机验证码
   * @return
   */
  public static String getNumeral(){
    String code = "";
    for (int i = 0; i < 6; i++) {
      code = code + (int)(Math.random() * 9);
    }
    return code;
  }

  /**
   * 2.纯字母随机验证码
   * @return
   */
  public static String getAlphabet(){
    String code = "";
    char[] ch = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K','L', 'M', 'N', 'O', 'P', 'Q','R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    for (int i = 0; i < 6; i++) {
      int index = (int)(Math.random() * ch.length);
      code = code + ch[index];
    }
    return code;
  }

  /**
   * 3.纯汉字随机验证码
   * @return
   */
  public static String getChinese(){
    String code = "";
    char[] ch = {'且', '随', '疾', '风','前', '行'};
    for (int i = 0; i < 6; i++) {
      int index = (int)(Math.random() * ch.length);
      code = code + ch[index];
    }
    return code;
  }

  public static void main(String[] args) {
    System.out.println("纯数字验证码："+getNumeral());
    System.out.println("纯字母验证码："+getAlphabet());
    System.out.println("纯汉字验证码："+getChinese());
  }
}
