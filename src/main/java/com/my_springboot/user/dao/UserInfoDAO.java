package com.my_springboot.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my_springboot.user.pojo.UserInfoDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 * 用户信息Mapper 接口
 * </p>
 *
 * @author JiHC
 * @since 2020-08-21
 */
public interface UserInfoDAO extends BaseMapper<UserInfoDO> {

    /**
     * 查询用户列表
     *
     * @return users
     */
    List<UserInfoDO> listUsers(Page<UserInfoDO> page);
}
