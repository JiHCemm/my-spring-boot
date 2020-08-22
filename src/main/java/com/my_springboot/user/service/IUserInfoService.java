package com.my_springboot.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my_springboot.user.pojo.UserInfoDO;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 用户信息服务类
 * </p>
 *
 * @author JiHC
 * @since 2020-08-21
 */
public interface IUserInfoService extends IService<UserInfoDO> {

    Page<UserInfoDO> listUserPage(Page<UserInfoDO> page);
}
