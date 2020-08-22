package com.my_springboot.rbac.service.impl;

import com.my_springboot.rbac.pojo.RolePermission;
import com.my_springboot.rbac.dao.RolePermissionDAO;
import com.my_springboot.rbac.service.IRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色权限关联 服务实现类
 * </p>
 *
 * @author JiHC
 * @since 2020-08-22
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionDAO, RolePermission> implements IRolePermissionService {

}
