package com.my_spring_boot.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my_spring_boot.util.IdGenerator;
import org.apache.shiro.codec.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import weixin.popular.util.SignatureUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JiHC
 * @date 2020/8/3
 */
public class WechatUtil {

  /**
   * 登录授权 接收小程序发送的code，并携带appid、appsecret、code发送到微信服务器。 微信服务器接收开发者服务器发送的appid、appsecret、code进行校验。
   * 校验通过后向开发者服务器发送session_key、openid。
   *
   * @param code code
   */
  public static JSONObject getSessionKeyOrOpenId(String code) {
    String requestUrl = WechatConstant.NOTIFY_URL;
    Map<String, String> requestUrlParam = new HashMap<>();
    // https://mp.weixin.qq.com/wxopen/devprofile?action=get_profile&token=164113089&lang=zh_CN
    //小程序端返回的code
    requestUrlParam.put("js_code", code);
    //小程序appId
    requestUrlParam.put("appid", WechatConstant.APP_ID);
    //小程序secret
    requestUrlParam.put("secret", WechatConstant.APP_SECRET);
    //默认参数
    requestUrlParam.put("grant_type", WechatConstant.GRANT_TYPE);
    //发送post请求读取调用微信接口获取openid用户唯一标识
    JSONObject jsonObject = JSON.parseObject(HttpClientUtil.doPost(requestUrl, requestUrlParam));
    return jsonObject;
  }

  /**
   * 要用到证书,退款时
   */
  public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
    // 被加密的数据
    byte[] dataByte = Base64.decode(encryptedData);
    // 加密秘钥
    byte[] keyByte = Base64.decode(sessionKey);
    // 偏移量
    byte[] ivByte = Base64.decode(iv);
    try {
      // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
      int base = 16;
      if (keyByte.length % base != 0) {
        int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
        byte[] temp = new byte[groups * base];
        Arrays.fill(temp, (byte) 0);
        System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
        keyByte = temp;
      }
      // 初始化
      Security.addProvider(new BouncyCastleProvider());
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
      SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
      AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
      parameters.init(new IvParameterSpec(ivByte));
      cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
      byte[] resultByte = cipher.doFinal(dataByte);
      if (null != resultByte && resultByte.length > 0) {
        String result = new String(resultByte, "UTF-8");
        return JSON.parseObject(result);
      }
    } catch (Exception e) {
    }
    return null;
  }

  /**
   * 小程序成功获取预付信息，并进行二次签名
   */
  public static Map<String, String> requestPayment(String prepay_id) {
    String timeStamp = (System.currentTimeMillis() / 1000) + "";
    String nonceStr = IdGenerator.uuid();
    String _package = "prepay_id=" + prepay_id;

    Map<String, String> signMap = new HashMap();
    signMap.put("appId", WechatConstant.APP_ID);
    signMap.put("timeStamp", timeStamp);
    signMap.put("nonceStr", nonceStr);
    signMap.put("package", _package);
    signMap.put("signType", WechatConstant.SIGN_TYPE);
    String paySign = SignatureUtil.generateSign(signMap, WechatConstant.API_KEY);
    signMap.put("paySign", paySign);

    return signMap;
  }

  /**
   * 获取IP地址
   *
   * @param request 请求
   */
  public static String getIpAddress(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }
}
