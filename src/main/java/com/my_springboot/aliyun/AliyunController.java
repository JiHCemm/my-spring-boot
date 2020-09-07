package com.my_springboot.aliyun;

import com.google.gson.Gson;
import com.my_springboot.common.Result;
import com.my_springboot.util.MathUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JiHC
 * @date 2020/8/4
 */
@RestController
@RequestMapping("/aliyun")
@Api(value = "AliyunController", tags = {"阿里云模块"})
public class AliyunController {

    @Autowired
    private SendSmsUtil sendSmsUtil;

    /**
     * 发送验证码
     *
     * @param phoneNumbers 手机号
     */
    @ResponseBody
    @ApiOperation("发送短信验证码")
    @PostMapping(value = "/sendSms")
    @ApiImplicitParam(name = "phoneNumbers", value = "手机号(多个用,拼接)", dataType = "string", required = true, paramType = "query")
    public Result sendSms(@RequestParam(value = "phoneNumbers") String phoneNumbers,
                          HttpServletRequest request) {
        HttpSession session = request.getSession ();
        //随机生成验证码
        String code = MathUtils.getNumeral ();
        Map<String, String> params = new HashMap<> ();
        params.put ("code", code);
        Gson gson = new Gson ();
        String templateParam = gson.toJson (params);
        String information = sendSmsUtil
                .aliSendSms (phoneNumbers, templateParam, AliyunConstant.TEMPLATE_CODE_LOGIN);
        session.setAttribute (phoneNumbers, code);
        session.setMaxInactiveInterval (3 * 60);
        if (information.equals ("ok")) {
            return Result.success (code);
        }
        return Result.error (400, "发送失败,请重新获取!");
    }

    /**
     * 发送短信
     *
     * @param phoneNumbers 手机号
     */
    @ResponseBody
    @ApiOperation("发送短信")
    @PostMapping(value = "/sendSMS")
    @ApiImplicitParam(name = "phoneNumbers", value = "手机号(多个用,拼接)", dataType = "string", required = true, paramType = "query")
    public Result sendSMS(@RequestParam(value = "phoneNumbers") String phoneNumbers,
                          HttpServletRequest request) {
        HttpSession session = request.getSession ();
        String param1 = request.getParameter ("param1");
        String param2 = request.getParameter ("param2");
        Map<String, String> params = new HashMap<> ();
        params.put ("code", "param1");
        params.put ("param2", param1);
        Gson gson = new Gson ();
        String code = gson.toJson (params);
        String information = sendSmsUtil
                .aliSendSms (phoneNumbers, code, AliyunConstant.TEMPLATE_CODE_WAYBILL_NO);
        session.setAttribute (phoneNumbers, "");
        session.setMaxInactiveInterval (3 * 60);
        if (information.equals ("ok")) {
            return Result.success (params);
        }
        return Result.error (400, "发送失败,请重新获取!");
    }

    /**
     * 上传文件
     *
     * @param file 上传对象
     */
    @ResponseBody
    @ApiOperation("后端测试接口-上传文件")
    @PostMapping(value = "/test/ossUpload")
    public Result uploadPicture(@RequestBody MultipartFile file) {
        return Result.success (OSSFileUtil.putObject (file));
    }

    /**
     * 获取文件访问路径
     *
     * @param fileName 文件名称
     */
    @ResponseBody
    @ApiOperation("后端测试接口-获取文件访问路径")
    @GetMapping(value = "/test/ossGetPath")
    @ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "string", required = true, paramType = "query")
    public Result viewPicture(@RequestParam(value = "fileName") String fileName) {
        return Result.success (OSSFileUtil.getUrl (fileName));
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名称
     */
    @ResponseBody
    @ApiOperation("后端测试接口-删除文件")
    @PostMapping(value = "/test/ossRemove")
    @ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "string", required = true, paramType = "query")
    public Result removePicture(@RequestParam(value = "fileName") String fileName) {
        OSSFileUtil.deleteObject (fileName);
        return Result.success ();
    }
}
