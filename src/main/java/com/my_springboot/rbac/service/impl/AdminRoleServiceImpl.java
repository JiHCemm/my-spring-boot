package com.my_springboot.rbac.service.impl;

import com.my_springboot.rbac.pojo.AdminRole;
import com.my_springboot.rbac.dao.AdminRoleDAO;
import com.my_springboot.rbac.service.IAdminRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关联 服务实现类
 * </p>
 *
 * @author JiHC
 * @since 2020-08-22
 */
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleDAO, AdminRole> implements IAdminRoleService {

}
