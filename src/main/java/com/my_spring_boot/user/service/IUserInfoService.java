package com.my_spring_boot.user.service;

import com.my_spring_boot.common.Result;
import com.my_spring_boot.user.pojo.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JiHC
 * @since 2020-09-20
 */
public interface IUserInfoService extends IService<UserInfo> {

    Result listByCondition(Integer pageNum, Integer pageSize, UserInfo userInfo);
}
