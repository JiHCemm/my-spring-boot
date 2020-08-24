package com.my_springboot.rbac.controller;


import com.my_springboot.auth.TokenUtil;
import com.my_springboot.auth.UserLoginToken;
import com.my_springboot.constant.ResultEnum;
import com.my_springboot.rbac.pojo.Admin;
import com.my_springboot.rbac.pojo.Permission;
import com.my_springboot.rbac.pojo.Role;
import com.my_springboot.rbac.service.IAdminService;
import com.my_springboot.rbac.service.IPermissionService;
import com.my_springboot.rbac.service.IRoleService;
import com.my_springboot.util.DateUtil;
import com.my_springboot.util.MD5Util;
import com.my_springboot.common.Result;
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
    @PostMapping(value = "/user/saveAdmin")
    @UserLoginToken
    public Result saveAdmin(@RequestBody Admin admin) {
        if (null == admin) {
            return Result.error (ResultEnum.NULL_PARAM.getCode (), ResultEnum.NULL_PARAM.getMsg ());
        }
        if (null == admin.getUserName () || "".equals (admin.getUserName ())) {
            return Result.error (ResultEnum.NULL_PARAM.getCode (),
                    "Required String parameter 'userName' is not present");
        }
        String password = admin.getPassword ();
        if (null == password || "".equals (password)) {
            return Result.error (ResultEnum.NULL_PARAM.getCode (),
                    "Required String parameter 'password' is not present");
        }
        admin.setPassword (MD5Util.MD5 (password));
        admin.setCreator (TokenUtil.getTokenUserId ());
        admin.setCreateTime (DateUtil.getCurrentTime ());
        return Result.success (adminService.save (admin));
    }

    @ApiOperation("修改管理员")
    @PutMapping(value = "/user/updateAdmin")
    @UserLoginToken
    public Result updateAdmin(@RequestBody Admin admin) {
        if (null == admin) {// 参数空指针
            return Result.error (ResultEnum.NULL_PARAM.getCode (), ResultEnum.NULL_PARAM.getMsg ());
        }
        String id = admin.getId ();
        if (null == id || "".equals (id)) {
            return Result.error (ResultEnum.NULL_PARAM.getCode (),
                    "Required String parameter 'id' is not present");
        }
        Admin originalData = adminService.getById (id);
        if (null == originalData) {// 未找到
            return Result.error (ResultEnum.NOT_FOUND.getCode (), ResultEnum.NOT_FOUND.getMsg ());
        }
        if (originalData.getIsDelete () == 1) {// 数据被删除
            return Result.error (ResultEnum.DATA_DELETE.getCode (), ResultEnum.DATA_DELETE.getMsg ());
        }
        admin.setUpdater (TokenUtil.getTokenUserId ());// 修改人
        admin.setUpdateTime (DateUtil.getCurrentTime ());// 修改时间
        return Result.success (adminService.updateById (admin));
    }

    @ApiOperation("删除管理员")
    @DeleteMapping(value = "/user/removeAdmin")
    @ApiImplicitParam(name = "id", value = "管理员id", dataType = "string", required = true, paramType = "query")
    @UserLoginToken
    public Result removeAdmin(@RequestParam String id) {
        Admin admin = adminService.getById (id);
        if (null == admin) {// 未找到
            return Result.error (ResultEnum.NOT_FOUND.getCode (), ResultEnum.NOT_FOUND.getMsg ());
        }
        if (admin.getIsDelete () == 1) {// 数据已被删除
            return Result.error (ResultEnum.DATA_DELETE.getCode (), ResultEnum.DATA_DELETE.getMsg ());
        }
        admin.setIsDelete (1);// 逻辑删除
        return Result.success (adminService.updateById (admin));
    }

    @ApiOperation("管理员登陆")
    @PostMapping(value = "/user/login")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "string", required = true, paramType = "query", defaultValue = "admin"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "string", required = true, paramType = "query", defaultValue = "admin123")
    })
    public Result login(@RequestParam String userName, @RequestParam String password) {
        return adminService.login (userName, password);
    }

    @ApiOperation("条件查询管理员列表")
    @PostMapping(value = "/user/listAdminsPage")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query", defaultValue = "10")
    })
    public Result listAdminsPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestBody Admin admin) {
        return adminService.listByCondition (pageNum, pageSize, admin);
    }

    @ApiOperation("添加角色")
    @PostMapping(value = "/role/saveRole")
    @UserLoginToken
    public Result saveRole(@RequestBody Role role) {
        if (null == role) {
            return Result.error (ResultEnum.NULL_PARAM.getCode (), ResultEnum.NULL_PARAM.getMsg ());
        }
        if (null == role.getRoleName () || "".equals (role.getRoleName ())) {
            return Result.error (ResultEnum.NULL_PARAM.getCode (),
                    "Required String parameter 'roleName' is not present");
        }
        role.setCreator (TokenUtil.getTokenUserId ());
        role.setCreateTime (DateUtil.getCurrentTime ());
        roleService.save (role);
        return Result.success ();
    }

    @ApiOperation("修改角色")
    @PutMapping(value = "/role/updateRole")
    @UserLoginToken
    public Result updateRole(@RequestBody Role role) {
        if (null == role) {// 参数空指针
            return Result.error (ResultEnum.NULL_PARAM.getCode (), ResultEnum.NULL_PARAM.getMsg ());
        }
        String id = role.getId ();
        if (null == id || "".equals (id)) {
            return Result.error (ResultEnum.NULL_PARAM.getCode (),
                    "Required String parameter 'id' is not present");
        }
        Role originalData = roleService.getById (id);
        if (null == originalData) {// 未找到
            return Result.error (ResultEnum.NOT_FOUND.getCode (), ResultEnum.NOT_FOUND.getMsg ());
        }
        if (originalData.getIsDelete () == 1) {// 数据被删除
            return Result.error (ResultEnum.DATA_DELETE.getCode (), ResultEnum.DATA_DELETE.getMsg ());
        }
        role.setUpdater (TokenUtil.getTokenUserId ());// 修改人
        role.setUpdateTime (DateUtil.getCurrentTime ());// 修改时间
        return Result.success (roleService.updateById (role));
    }

    @ApiOperation("删除角色")
    @DeleteMapping(value = "/role/removeRole")
    @ApiImplicitParam(name = "id", value = "角色id", dataType = "string", required = true, paramType = "query")
    @UserLoginToken
    public Result removeRole(@RequestParam String id) {
        Role role = roleService.getById (id);
        if (null == role) {// 未找到
            return Result.error (ResultEnum.NOT_FOUND.getCode (), ResultEnum.NOT_FOUND.getMsg ());
        }
        if (role.getIsDelete () == 1) {// 数据已被删除
            return Result.error (ResultEnum.DATA_DELETE.getCode (), ResultEnum.DATA_DELETE.getMsg ());
        }
        role.setIsDelete (1);// 逻辑删除
        return Result.success (roleService.updateById (role));
    }

    @ApiOperation("条件查询角色列表")
    @PostMapping(value = "/role/listRolesPage")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query", defaultValue = "10")
    })
    public Result listRolesPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestBody Role role) {
        return roleService.listByCondition (pageNum, pageSize, role);
    }

    @ApiOperation("添加权限")
    @PostMapping(value = "/permission/savePermission")
    @UserLoginToken
    public Result savePermission(@RequestBody Permission permission) {
        if (null == permission) {
            return Result.error (ResultEnum.NULL_PARAM.getCode (), ResultEnum.NULL_PARAM.getMsg ());
        }
        if (null == permission.getPermissionName () || "".equals (permission.getPermissionName ())) {
            return Result.error (ResultEnum.NULL_PARAM.getCode (),
                    "Required String parameter 'permissionName' is not present");
        }
        permission.setCreator (TokenUtil.getTokenUserId ());
        permission.setCreateTime (DateUtil.getCurrentTime ());
        permissionService.save (permission);
        return Result.success ();
    }

    @ApiOperation("修改权限")
    @PutMapping(value = "/permission/updatePermission")
    @UserLoginToken
    public Result updatePermission(@RequestBody Permission permission) {
        if (null == permission) {// 参数空指针
            return Result.error (ResultEnum.NULL_PARAM.getCode (), ResultEnum.NULL_PARAM.getMsg ());
        }
        String id = permission.getId ();
        if (null == id || "".equals (id)) {
            return Result.error (ResultEnum.NULL_PARAM.getCode (),
                    "Required String parameter 'id' is not present");
        }
        Permission originalData = permissionService.getById (id);
        if (null == originalData) {// 未找到
            return Result.error (ResultEnum.NOT_FOUND.getCode (), ResultEnum.NOT_FOUND.getMsg ());
        }
        if (originalData.getIsDelete () == 1) {// 数据被删除
            return Result.error (ResultEnum.DATA_DELETE.getCode (), ResultEnum.DATA_DELETE.getMsg ());
        }
        permission.setUpdater (TokenUtil.getTokenUserId ());// 修改人
        permission.setUpdateTime (DateUtil.getCurrentTime ());// 修改时间
        return Result.success (permissionService.updateById (permission));
    }

    @ApiOperation("删除权限")
    @DeleteMapping(value = "/permission/removePermission")
    @ApiImplicitParam(name = "id", value = "角色id", dataType = "string", required = true, paramType = "query")
    @UserLoginToken
    public Result removePermission(@RequestParam String id) {
        Permission permission = permissionService.getById (id);
        if (null == permission) {// 未找到
            return Result.error (ResultEnum.NOT_FOUND.getCode (), ResultEnum.NOT_FOUND.getMsg ());
        }
        if (permission.getIsDelete () == 1) {// 数据已被删除
            return Result.error (ResultEnum.DATA_DELETE.getCode (), ResultEnum.DATA_DELETE.getMsg ());
        }
        permission.setIsDelete (1);// 逻辑删除
        return Result.success (permissionService.updateById (permission));
    }

    @ApiOperation("条件查询权限列表")
    @PostMapping(value = "/permission/listPermissionsPage")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query", defaultValue = "10")
    })
    public Result listPermissionsPage(@RequestParam Integer pageNum,
                                      @RequestParam Integer pageSize,
                                      @RequestBody Permission permission) {
        return permissionService.listByCondition (pageNum, pageSize, permission);
    }
}

