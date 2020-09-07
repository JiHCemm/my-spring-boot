package com.my_spring_boot.aliyun;

/**
 * @author JiHC
 * @date 2020/7/28
 */
public class AliyunConstant {

  //[阿里云]外网 Endpoint
  public final static String ENDPOINT = "oss-cn-shenzhen.aliyuncs.com";
  //[阿里云] accessKeyId 访问密钥id
  public final static String ACCESS_KEY_ID = "阿里云账号访问密钥id";
  //[阿里云] accessKeySecret 访问密钥key
  public final static String ACCESS_KEY_SECRET = "阿里云账号访问密钥key";

  //============================= 文件处理 begin =============================
  //[阿里云] bucket 存储空间名称
  public final static String BUCKET_NAME = "bucket名称";
  //[阿里云]  文件上传地址
  public final static String FILE_PATH_UPLOAD = "oss访问地址";
  //============================= 文件处理 end =============================

  //============================= 短信 begin =============================
  //签名
  public final static String SIGN_NAME = "短信签名";
  //短信模板代码-登录
  public final static String TEMPLATE_CODE_LOGIN = "模版CODE";
  //短信模板代码-发送运单号
  public final static String TEMPLATE_CODE_WAYBILL_NO = "SMS_200722737";
  //============================= 短信 end =============================
}
