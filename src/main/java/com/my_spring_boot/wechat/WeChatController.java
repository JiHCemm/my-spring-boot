package com.my_spring_boot.wechat;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my_spring_boot.common.Result;
import com.my_spring_boot.util.IdGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import weixin.popular.api.PayMchAPI;
import weixin.popular.bean.paymch.Unifiedorder;
import weixin.popular.bean.paymch.UnifiedorderResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 微信用户信息 前端控制器
 * </p>
 *
 * @author
 * @since 2020-08-03
 */
@RestController
@RequestMapping("/wechat")
@Api(value = "WeChatController", tags = {"微信模块"})
public class WeChatController {

    @Autowired
//    private UsersInfoMapper userMapper;

    @PostMapping("getAccessToken")
    @ApiOperation("获取accessToken")
    @ResponseBody
    public Result getAccessToken() {
        return Result.success (WechatConstant.accessToken);
    }

    /**
     * 微信用户登录详情
     */
    @PostMapping("login")
    @ApiOperation("微信登录授权(添加微信用户)")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "code", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "rawData", value = "不包括敏感信息的原始数据字符串", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "signature", value = "用于校验用户信息", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "encrypteData", value = "包括敏感数据在内的完整用户信息的加密数据", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "iv", value = "加密算法的初始向量", dataType = "string", required = true, paramType = "query")
    })
    public Result user_login(@RequestParam(value = "code", required = true) String code,
                             @RequestParam(value = "rawData", required = false) String rawData,
                             @RequestParam(value = "signature", required = false) String signature,
                             @RequestParam(value = "encrypteData", required = false) String encrypteData,
                             @RequestParam(value = "iv", required = false) String iv, HttpServletRequest request) {
        // 用户非敏感信息：rawData
        // 签名：signature
        JSONObject rawDataJson = JSON.parseObject (rawData);
        // 1.接收小程序发送的code
        // 2.开发者服务器 登录凭证校验接口 appi + appsecret + code
        JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId (code);
        // 3.接收微信接口服务 获取返回的参数
        String openid = SessionKeyOpenId.getString ("openid");
        String sessionKey = SessionKeyOpenId.getString ("session_key");

        // 4.校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)
//    String signature2 = DigestUtils.sha1Hex(rawData + sessionKey);
//    if (!signature.equals(signature2)) {
//      return new Result(500, "签名校验失败", null);
//    }
//        // 5.根据返回的User实体类，判断用户是否是新用户，是的话，将用户信息存到数据库；不是的话，更新最新登录时间
//        UsersInfo user = this.userMapper.selectByOpenid (openid);
//        // uuid生成唯一key，用于维护微信小程序用户与服务端的会话
//        String skey = IdGenerator.uuid ();
//        if (user == null) {
//            // 用户信息入库
//            String nickName = rawDataJson.getString ("nickName");
//            String avatarUrl = rawDataJson.getString ("avatarUrl");
//            String gender = rawDataJson.getString ("gender");
//            String city = rawDataJson.getString ("city");
//            String country = rawDataJson.getString ("country");
//            String province = rawDataJson.getString ("province");
//            String phone = request.getParameter ("phone");
//            user = new UsersInfo ();
//            user.setOpenid (openid);
//            user.setSkey (skey);
//            user.setLoginTime (new Date ());
//            user.setLastVisitTime (new Date ());
//            user.setSessionKey (sessionKey);
//            user.setPhone (phone);
//            user.setCity (city);
//            user.setProvince (province);
//            user.setCountry (country);
//            user.setAvatarUrl (avatarUrl);
//            user.setGender (Integer.parseInt (gender));
//            user.setNickName (nickName);
//            user.setIpAddress (WechatUtil.getIpAddress (request));
//            user.setIntegral (0);
//
//            this.userMapper.insert (user);
//        } else {
//            // 已存在，更新用户登录时间
//            user.setLastVisitTime (new Date ());
//            // 重新设置会话skey
//            user.setSkey (skey);
//            this.userMapper.updateById (user);
//        }

        //encrypteData比rowData多了appid和openid
        //JSONObject userInfo = WechatUtil.getUserInfo(encrypteData, sessionKey, iv);

        //6. 把新的skey返回给小程序
        return Result.success ();
    }

    /**
     * 获取用户支付签名
     *
     * @param openid   openid
     * @param totalFee 总金额
     */
    @PostMapping("getWechatPaySign")
    @ApiOperation("获取用户支付签名")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "openid", dataType = "string", required = true, paramType = "query"),
            @ApiImplicitParam(name = "totalFee", value = "总金额", dataType = "string", required = true, paramType = "query")
    })
    public Result getWechatPaySign(@RequestParam(value = "openid") String openid,
                                   @RequestParam(value = "totalFee") String totalFee, HttpServletRequest request) {

        String goodsName = request.getParameter ("goodsName");
        String outTradeNo = IdGenerator.uuid ();
        Unifiedorder unifiedorder = new Unifiedorder ();
        unifiedorder.setAppid (WechatConstant.APP_ID);
        unifiedorder.setMch_id (WechatConstant.MCH_ID);
        unifiedorder.setDevice_info ("信美恋爱" + goodsName + "支付成功!");
        unifiedorder.setNonce_str (IdGenerator.uuid ());
        unifiedorder.setSign_type (WechatConstant.SIGN_TYPE);
        unifiedorder.setBody ("信美恋爱" + goodsName);
        unifiedorder.setOut_trade_no (outTradeNo);
        unifiedorder.setTotal_fee (totalFee);
        String ip = WechatUtil.getIpAddress (request);
        unifiedorder.setSpbill_create_ip (ip); //用户IP地址
        unifiedorder.setNotify_url (WechatConstant.NOTIFY_URL);  //回调地址
        unifiedorder.setTrade_type (WechatConstant.TRADE_TYPE);
        unifiedorder.setOpenid (openid);

        //微信支付接口
        UnifiedorderResult payResult = PayMchAPI
                .payUnifiedorder (unifiedorder, WechatConstant.API_KEY);

        if (payResult == null) {
            return Result.error (404, "微信预付订单获取失败");
        }

        if (!payResult.getReturn_code ().equals ("SUCCESS")) {
            return Result.error (500, payResult.getReturn_msg () + "|" + payResult.getErr_code_des ());
        }

        //预支付结果再次签名
        String prepayId = payResult.getPrepay_id ();
        Map<String, String> result = WechatUtil
                .requestPayment (prepayId);

        result.put ("orderUuid", outTradeNo);
        return Result.success (200, "", result);
    }

}

