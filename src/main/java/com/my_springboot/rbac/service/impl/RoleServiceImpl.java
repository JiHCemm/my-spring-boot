package com.my_springboot.rbac.service.impl;

import com.my_springboot.rbac.pojo.Role;
import com.my_springboot.rbac.dao.RoleDAO;
import com.my_springboot.rbac.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author JiHC
 * @since 2020-08-22
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDAO, Role> implements IRoleService {

}
