package com.my_springboot.rbac.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my_springboot.common.Result;
import com.my_springboot.rbac.pojo.Permission;
import com.my_springboot.rbac.dao.PermissionDAO;
import com.my_springboot.rbac.pojo.Role;
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

    @Override
    public Result listByCondition(Integer pageNum, Integer pageSize, Permission permission) {
        if (null == pageNum || null == pageSize) {// 列表
            return Result.success (baseMapper.listByCondition (null, permission));
        } else {// 分页
            Page<Permission> page = new Page<> (pageNum, pageSize);
            page.setRecords (baseMapper.listByCondition (page, permission));
            return Result.success (page);
        }
    }
}
