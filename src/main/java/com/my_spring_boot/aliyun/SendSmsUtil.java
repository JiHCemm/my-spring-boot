package com.my_spring_boot.aliyun;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 短信发送工具
 *
 * @author JiHC
 * @date 2020/8/4
 */
@Service
public class SendSmsUtil {

  public String aliSendSms(String phoneNumbers, String templateParam,String templateCode) {
    String result = "";
    DefaultProfile profile = DefaultProfile
        .getProfile("cn-hangzhou", AliyunConstant.ACCESS_KEY_ID, AliyunConstant.ACCESS_KEY_SECRET);
    IAcsClient client = new DefaultAcsClient(profile);

    CommonRequest request = new CommonRequest();
    request.setSysMethod(MethodType.POST);
    request.setSysDomain("dysmsapi.aliyuncs.com");
    request.setSysVersion("2017-05-25");
    request.setSysAction("SendSms");
    request.putQueryParameter("RegionId", "cn-hangzhou");
    request.putQueryParameter("PhoneNumbers", phoneNumbers);
    request.putQueryParameter("SignName", AliyunConstant.SIGN_NAME);
    request.putQueryParameter("TemplateCode", templateCode);
    request.putQueryParameter("TemplateParam", templateParam);
    try {
      CommonResponse response = client.getCommonResponse(request);
      System.out.println(response.getData());
      String infojson = response.getData();
      Gson gson2 = new Gson();
      Map map = gson2.fromJson(infojson, Map.class);
      String codes = map.get("Message").toString();
      System.out.println("codes=" + codes);
      if (codes.equals("OK")) {
        result = "ok";
      } else {
        result = "not_ok";
      }
    } catch (ServerException e) {
      e.printStackTrace();
    } catch (ClientException e) {
      e.printStackTrace();
    }
    return result;
  }

}
