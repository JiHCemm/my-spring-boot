package com.my_springboot.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my_springboot.user.pojo.UserInfoDO;
import com.my_springboot.user.dao.UserInfoDAO;
import com.my_springboot.user.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息服务实现类
 * </p>
 *
 * @author JiHC
 * @since 2020-08-21
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDAO, UserInfoDO> implements
        IUserInfoService {
    @Override
    public Page<UserInfoDO> listUserPage(Page<UserInfoDO> page) {
        page.setRecords(baseMapper.listUsers(page));
        return page;
    }
}
