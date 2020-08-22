package com.my_springboot.rbac.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my_springboot.auth.TokenUtil;
import com.my_springboot.auth.UserLoginToken;
import com.my_springboot.constant.ResultEnum;
import com.my_springboot.rbac.pojo.Admin;
import com.my_springboot.rbac.service.IAdminService;
import com.my_springboot.rbac.service.IPermissionService;
import com.my_springboot.rbac.service.IRoleService;
import com.my_springboot.util.DateUtil;
import com.my_springboot.util.MD5Util;
import com.my_springboot.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * Role-Based Access Control，基于角色的访问控制
 * </p>
 *
 * @author JiHC
 * @since 2020-08-22
 */
@RestController
@RequestMapping("/rbac")
@Api(value = "RBACController", tags = {"【权限模块】"})
public class RBACController {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;

    @ApiOperation("添加管理员")
    @PostMapping(value = "/saveAdmin")
    @UserLoginToken
    public Result saveAdmin(@RequestBody Admin admin) {
        admin.setPassword (MD5Util.MD5 (admin.getPassword ()));
        admin.setCreator (TokenUtil.getTokenUserId ());
        admin.setCreateTime (DateUtil.getCurrentTime ());
        adminService.save (admin);
        return Result.success ();
    }

    @ApiOperation("修改管理员")
    @PutMapping(value = "/updateAdmin")
    @UserLoginToken
    public Result updateAdmin(@RequestBody Admin admin) {
        String id=admin.getId ();
        if(null==admin||null==id||"".equals (id)){// 参数空指针
            return Result.error (ResultEnum.NULL_PARAM.getCode (),ResultEnum.NULL_PARAM.getMsg ());
        }
        Admin originalAdmin=adminService.getById (id);
        if(null==originalAdmin){// 未找到
            return Result.error (ResultEnum.NOT_FOUND.getCode (),ResultEnum.NOT_FOUND.getMsg ());
        }
        if(originalAdmin.getIsDelete ()==1){// 数据被删除
            return Result.error(ResultEnum.DATA_DELETE.getCode (),ResultEnum.DATA_DELETE.getMsg ());
        }
        admin.setUpdater (TokenUtil.getTokenUserId ());// 修改人
        admin.setUpdateTime (DateUtil.getCurrentTime ());// 修改时间
        return Result.success (adminService.updateById (admin));
    }

    @ApiOperation("删除管理员")
    @DeleteMapping(value = "/removeAdmin")
    @ApiImplicitParam(name = "id", value = "管理员id", dataType = "string", required = true, paramType = "query")
    @UserLoginToken
    public Result removeAdmin(@RequestParam String id) {
        Admin admin=adminService.getById (id);
        if(null==admin){// 未找到
            return Result.error (ResultEnum.NOT_FOUND.getCode (),ResultEnum.NOT_FOUND.getMsg ());
        }
        admin.setIsDelete (1);// 逻辑删除
        adminService.updateById (admin);
        return Result.success ();
    }

    @ApiOperation("管理员登陆")
    @PostMapping(value = "/login")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "string", required = true, paramType = "query", defaultValue = "admin"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "string", required = true, paramType = "query", defaultValue = "admin123")
    })
    public Result login(@RequestParam String userName,@RequestParam String password) {
        return adminService.login (userName,password);
    }

    @ApiOperation("查询管理员分页")
    @PostMapping(value = "/listAdminPage")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", required = true, paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", required = true, paramType = "query", defaultValue = "10")
    })
    public Result listAdminPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return adminService.listAdminPage (new Page (pageNum, pageSize));
    }

}

