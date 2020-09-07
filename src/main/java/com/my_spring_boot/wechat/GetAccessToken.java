package com.my_spring_boot.wechat;

import com.alibaba.fastjson.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author JiHC
 * @date 2020/8/19
 */
@Component
public class GetAccessToken {

  public String getToken() throws Exception {
    String accessTokenUrl = WechatConstant.ACCESS_TOKEN_URL + "?grant_type=client_credential&appid="
        + WechatConstant.APP_ID + "&secret=" + WechatConstant.APP_SECRET;
    URL url = new URL(accessTokenUrl);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    connection.setRequestMethod("GET");
    connection.setDoOutput(true);
    connection.setDoInput(true);
    connection.connect();

    // 获取返回的字符
    InputStream inputStream = connection.getInputStream();
    int size = inputStream.available();
    byte[] bs = new byte[size];
    inputStream.read(bs);
    String message = new String(bs, "UTF-8");

    // 获取access_token
    JSONObject jsonObject = JSONObject.parseObject(message);
    return jsonObject.getString("access_token");
  }
  /**
   * access_token 是小程序的全局唯一调用凭据
   * access_token 的有效期为 2 个小时，需要定时刷新 access_token，重复获取会导致之前一次获取的
   * access_token 失效
   * 延迟一秒执行
   */
  @Scheduled(initialDelay = 1000, fixedDelay = 7000*1000 )
  public void getTouTiaoAccessToken(){
    try {
      //将获取到的token放到内存
      WechatConstant.accessToken = getToken();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
