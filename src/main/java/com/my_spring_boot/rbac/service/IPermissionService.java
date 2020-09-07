package com.my_spring_boot.rbac.service;

import com.my_spring_boot.common.Result;
import com.my_spring_boot.rbac.pojo.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author JiHC
 * @since 2020-08-22
 */
public interface IPermissionService extends IService<Permission> {

    Result listByCondition(Integer pageNum, Integer pageSize, Permission permission);

}
