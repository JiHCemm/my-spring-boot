package com.my_spring_boot.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my_spring_boot.user.pojo.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author JiHC
 * @since 2020-09-20
 */
public interface UserInfoDAO extends BaseMapper<UserInfo> {
    List<UserInfo> listByCondition(Page<UserInfo> page, @Param("userInfo") UserInfo userInfo);

}
