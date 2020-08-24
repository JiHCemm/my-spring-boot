package com.my_springboot.rbac.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my_springboot.rbac.pojo.Admin;
import com.my_springboot.rbac.pojo.Role;
import com.my_springboot.rbac.dao.RoleDAO;
import com.my_springboot.rbac.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my_springboot.common.Result;
import org.springframework.http.HttpStatus;
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

    @Override
    public Result listByCondition(Integer pageNum, Integer pageSize, Role role) {
        if (null == pageNum || null == pageSize) {// 列表
            return Result.success (baseMapper.listByCondition (null, role));
        } else {// 分页
            Page<Role> page = new Page<> (pageNum, pageSize);
            page.setRecords (baseMapper.listByCondition (page, role));
            return Result.success (page);
        }
    }

}
