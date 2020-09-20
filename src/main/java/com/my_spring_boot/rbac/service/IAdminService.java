package com.my_spring_boot.rbac.service;

import com.my_spring_boot.rbac.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.my_spring_boot.common.Result;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author JiHC
 * @since 2020-08-22
 */
public interface IAdminService extends IService<Admin> {

    Result login(String loginType, String loginName, String password);

    Result listByCondition(Integer pageNum, Integer pageSize, Admin admin);
}
