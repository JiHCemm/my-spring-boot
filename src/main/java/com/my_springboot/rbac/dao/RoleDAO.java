package com.my_springboot.rbac.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my_springboot.rbac.pojo.Admin;
import com.my_springboot.rbac.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author JiHC
 * @since 2020-08-22
 */
public interface RoleDAO extends BaseMapper<Role> {

    List<Role> listByCondition(Page<Role> page, @Param("role") Role role);

}
