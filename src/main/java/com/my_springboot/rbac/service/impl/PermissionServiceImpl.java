package com.my_springboot.rbac.service.impl;

import com.my_springboot.rbac.pojo.Permission;
import com.my_springboot.rbac.dao.PermissionDAO;
import com.my_springboot.rbac.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author JiHC
 * @since 2020-08-22
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDAO, Permission> implements IPermissionService {

}
