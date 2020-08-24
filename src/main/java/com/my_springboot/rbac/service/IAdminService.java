package com.my_springboot.rbac.service;

import com.my_springboot.rbac.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.my_springboot.common.Result;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author JiHC
 * @since 2020-08-22
 */
public interface IAdminService extends IService<Admin> {

    Result login(String userName, String password);

    Result listByCondition(Integer pageNum, Integer pageSize, Admin admin);
}
