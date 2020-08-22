package com.my_springboot.rbac.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my_springboot.rbac.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.my_springboot.user.pojo.UserInfoDO;
import com.my_springboot.util.Result;

import java.util.List;

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

    Result listByCondition(Admin admin);

    Result listAdminPage(Page<Admin> page);
}
