package com.my_springboot.rbac.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my_springboot.rbac.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author JiHC
 * @since 2020-08-22
 */
public interface PermissionDAO extends BaseMapper<Permission> {

    List<Permission> listByCondition(Page<Permission> page, @Param("permission") Permission permission);

}
