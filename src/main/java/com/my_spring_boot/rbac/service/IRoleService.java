package com.my_spring_boot.rbac.service;

import com.my_spring_boot.rbac.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.my_spring_boot.common.Result;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author JiHC
 * @since 2020-08-22
 */
public interface IRoleService extends IService<Role> {

    Result listByCondition(Integer pageNum, Integer pageSize, Role role);

}
