package com.my_springboot.user.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my_springboot.user.pojo.UserInfoDO;
import com.my_springboot.user.service.IUserInfoService;
import com.my_springboot.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
 * 用户信息前端控制器
 * </p>
 *
 * @author JiHC
 * @since 2020-08-21
 */
@RestController
@RequestMapping("/user")
@Api(value = "UserInfoController", tags = {"用户模块"})
public class UserInfoController {

    @Autowired
    IUserInfoService userInfoService;

    @ApiOperation("新增用户信息")
    @PostMapping(value = "/saveUser")
    public Result saveUser(@RequestBody UserInfoDO userInfoDO) {
        userInfoService.save(userInfoDO);
        return Result.success(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase());
    }

    @ApiOperation("根据问题id修改问题")
    @PutMapping(value = "/updateUser")
    public Result updateUser(@RequestBody UserInfoDO userInfoDO) {
        String id = userInfoDO.getId();
        if (null == id || "".equals(id)) {// 无内容
            return Result
                    .error(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
        }
        if (null==userInfoService.getById(id)) {// 未找到
            return Result
                    .error(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
        }
        userInfoService.updateById(userInfoDO);
        return Result.success();
    }

    @ApiOperation("根据id删除用户信息")
    @DeleteMapping(value = "/removeUser")
    @ApiImplicitParam(name = "id", value = "用户id", dataType = "string", required = true, paramType = "query")
    public Result removeUser(@RequestParam String id) {
        if (null == id || "".equals(id)) {// 无内容
            return Result
                    .error(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
        }
        if (null==userInfoService.getById(id)) {// 未找到
            return Result
                    .error(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
        }
        userInfoService.removeById(id);
        return Result.success();
    }

    @ApiOperation("查询信息分页")
    @PostMapping(value = "/listUserPage")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", required = true, paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", required = true, paramType = "query", defaultValue = "10")
    })
    public Result listUserPage(Integer pageNum, Integer pageSize) {
        Page<UserInfoDO> page = userInfoService.listUserPage(new Page(pageNum, pageSize));
        return Result.success(page);
    }
}

