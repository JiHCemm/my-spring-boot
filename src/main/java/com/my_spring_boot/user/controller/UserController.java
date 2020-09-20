package com.my_spring_boot.user.controller;


import com.my_spring_boot.auth.TokenUtil;
import com.my_spring_boot.auth.UserLoginToken;
import com.my_spring_boot.common.Result;
import com.my_spring_boot.constant.DatabaseEnum;
import com.my_spring_boot.constant.ResultEnum;
import com.my_spring_boot.rbac.service.IAdminService;
import com.my_spring_boot.user.pojo.UserInfo;
import com.my_spring_boot.user.service.IUserInfoService;
import com.my_spring_boot.util.DateUtils;
import com.my_spring_boot.util.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JiHC
 * @since 2020-09-20
 */
@RestController
@RequestMapping("/user")
@Api(value = "RBACController", tags = {"【用户模块】"})
public class UserController {

  private IUserInfoService userInfoService;
  private IAdminService adminService;

  @Autowired
  private UserController(IUserInfoService uis,IAdminService as){
    this.userInfoService=uis;
    this.adminService=as;
  }

  @ApiOperation("添加用户")
  @PostMapping(value = "/user/saveUser")
  @UserLoginToken
  public Result saveUser(@RequestBody UserInfo userInfo) {
    if (null == userInfo) {
      return Result.error (ResultEnum.NULL_PARAM.getCode (), ResultEnum.NULL_PARAM.getMsg ());
    }
    if (null == userInfo.getUserName () || "".equals (userInfo.getUserName ())) {
      return Result.error (ResultEnum.NULL_PARAM.getCode (),
          "Required String parameter 'userName' is not present");
    }
    String password = userInfo.getPassword ();
    if (null == password || "".equals (password)) {
      return Result.error (ResultEnum.NULL_PARAM.getCode (),
          "Required String parameter 'password' is not present");
    }
    userInfo.setPassword (MD5Utils.MD5 (password));
    userInfo.setCreater (TokenUtil.getTokenUserId ());
    userInfo.setCreateTime (DateUtils.getCurrentTime ());
    return Result.success (userInfoService.save (userInfo));
  }

  @ApiOperation("修改用户")
  @PutMapping(value = "/user/updateUser")
  @UserLoginToken
  public Result updateUser(@RequestBody UserInfo userInfo) {
    if (null == userInfo) {// 参数空指针
      return Result.error (ResultEnum.NULL_PARAM.getCode (), ResultEnum.NULL_PARAM.getMsg ());
    }
    String id = userInfo.getId ();
    if (null == id || "".equals (id)) {
      return Result.error (ResultEnum.NULL_PARAM.getCode (),
          "Required String parameter 'id' is not present");
    }
    UserInfo originalData = userInfoService.getById (id);
    if (null == originalData) {// 未找到
      return Result.error (ResultEnum.NOT_FOUND.getCode (), ResultEnum.NOT_FOUND.getMsg ());
    }
    if (originalData.getIsDelete () .equals(DatabaseEnum.IS_DELETE.getValue())) {// 数据被删除
      return Result.error (ResultEnum.DATA_DELETE.getCode (), ResultEnum.DATA_DELETE.getMsg ());
    }
    userInfo.setUpdater (TokenUtil.getTokenUserId ());// 修改人
    userInfo.setUpdateTime (DateUtils.getCurrentTime ());// 修改时间
    return Result.success (userInfoService.updateById (userInfo));
  }

  @ApiOperation("删除用户")
  @DeleteMapping(value = "/user/removeUser")
  @ApiImplicitParam(name = "id", value = "用户id", dataType = "string", required = true, paramType = "query")
  @UserLoginToken
  public Result removeUser(@RequestParam String id) {
    UserInfo userInfo = userInfoService.getById (id);
    if (null == userInfo) {// 未找到
      return Result.error (ResultEnum.NOT_FOUND.getCode (), ResultEnum.NOT_FOUND.getMsg ());
    }
    if (userInfo.getIsDelete () .equals(DatabaseEnum.IS_DELETE.getValue())) {// 数据已被删除
      return Result.error (ResultEnum.DATA_DELETE.getCode (), ResultEnum.DATA_DELETE.getMsg ());
    }
    userInfo.setIsDelete (DatabaseEnum.IS_DELETE.getValue());// 逻辑删除
    return Result.success (userInfoService.updateById (userInfo));
  }

  @ApiOperation("用户登陆")
  @PostMapping(value = "/user/login")
  @ResponseBody
  @ApiImplicitParams({
      @ApiImplicitParam(name = "userName", value = "用户名", dataType = "string", required = true, paramType = "query", defaultValue = "JiHC"),
      @ApiImplicitParam(name = "password", value = "密码", dataType = "string", required = true, paramType = "query", defaultValue = "admin123")
  })
  public Result login(@RequestParam String userName, @RequestParam String password) {
    return adminService.login (DatabaseEnum.USER.getValue(),userName, password);
  }

  @ApiOperation("条件查询用户列表")
  @PostMapping(value = "/user/listUsersPage")
  @ResponseBody
  @ApiImplicitParams({
      @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", defaultValue = "1"),
      @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query", defaultValue = "10")
  })
  public Result listUsersPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestBody UserInfo userInfo) {
    return userInfoService.listByCondition (pageNum, pageSize, userInfo);
  }
}

