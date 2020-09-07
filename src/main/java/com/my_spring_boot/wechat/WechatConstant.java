package com.my_spring_boot.wechat;

/**
 * @author JiHC
 * @date 2020/7/31
 */
public class WechatConstant {

  //微信小程序appid
  public final static String APP_ID = "wx24e555c077e702f3";
  //微信小程序appsecret
  public final static String APP_SECRET = "f549e353a9cf3ce16256756b8a8e229e";
  //微信商户号
  public final static String MCH_ID="1582954481";
  //微信支付的商户密钥
  public final static  String API_KEY = "washimayun1302525820515014354439";
  //签名方式
  public static final String SIGN_TYPE = "MD5";
  //支付成功后的服务器回调url
  public static final String NOTIFY_URL="https://api.weixin.qq.com/sns/jscode2session";
  //微信统一下单接口地址
  public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
  //配置自己的域名
  public static final String DOMAIN = "配置自己的域名";
  //微信支付方式 JSAPI--公众号支付-小程序支付
  public static final String TRADE_TYPE = "JSAPI";
  // 微信登录授权类型(固定参数)
  public static final String GRANT_TYPE = "authorization_code";
  // access_token地址
  public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

  public static String accessToken;
}
