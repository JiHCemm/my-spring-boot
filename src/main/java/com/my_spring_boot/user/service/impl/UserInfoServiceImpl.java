package com.my_spring_boot.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my_spring_boot.common.Result;
import com.my_spring_boot.user.pojo.UserInfo;
import com.my_spring_boot.user.dao.UserInfoDAO;
import com.my_spring_boot.user.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my_spring_boot.util.MD5Utils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JiHC
 * @since 2020-09-20
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDAO, UserInfo> implements IUserInfoService {
    @Override
    public Result listByCondition(Integer pageNum, Integer pageSize, UserInfo userInfo) {
        if (null != userInfo) {
            String password = userInfo.getPassword ();
            if (null != password && !"".equals (password)) {
                userInfo.setPassword (MD5Utils.MD5 (password));
            }
        }
        if (null == pageNum || null == pageSize) {// 列表
            return Result.success (baseMapper.listByCondition (null, userInfo));
        } else {// 分页
            Page<UserInfo> page = new Page<> (pageNum, pageSize);
            page.setRecords (baseMapper.listByCondition (page, userInfo));
            return Result.success (page);
        }
    }
}
