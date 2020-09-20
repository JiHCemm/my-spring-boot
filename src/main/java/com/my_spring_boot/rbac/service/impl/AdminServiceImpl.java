package com.my_spring_boot.rbac.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my_spring_boot.auth.TokenService;
import com.my_spring_boot.constant.DatabaseEnum;
import com.my_spring_boot.constant.ResultEnum;
import com.my_spring_boot.rbac.pojo.Admin;
import com.my_spring_boot.rbac.dao.AdminDAO;
import com.my_spring_boot.rbac.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my_spring_boot.user.dao.UserInfoDAO;
import com.my_spring_boot.user.pojo.UserInfo;
import com.my_spring_boot.user.service.IUserInfoService;
import com.my_spring_boot.util.MD5Utils;
import com.my_spring_boot.common.Result;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author JiHC
 * @since 2020-08-22
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminDAO, Admin> implements IAdminService {

    private TokenService tokenService;
    private UserInfoDAO userInfoDAO;

    @Autowired
    private AdminServiceImpl(TokenService ts, UserInfoDAO uid) {
        this.tokenService = ts;
        this.userInfoDAO = uid;
    }
    @SuppressWarnings("unchecked")
    @Override
    public Result login(String loginType, String loginName, String password) {
        if (null == loginName || "".equals(loginName)) {
            return Result.error(ResultEnum.NULL_PARAM.getCode(),
                    "Required String parameter 'loginName' is not present");
        }
        if (null == password || "".equals(password)) {
            return Result.error(ResultEnum.NULL_PARAM.getCode(),
                    "Required String parameter 'password' is not present");
        }
        String token = "";
        if (loginType.equals(DatabaseEnum.ADMIN.getValue())) {// 管理员登录
            Admin admin = new Admin();
            admin.setUserName(loginName);
            admin.setPassword(MD5Utils.MD5(password));
            List<Admin> admins = baseMapper.listByCondition(null, admin);
            if (null == admins || admins.size() == 0) {
                return Result.error(ResultEnum.NOT_FOUND.getCode(), ResultEnum.NOT_FOUND.getMsg());
            }
            token = tokenService.getToken(admins.get(0).getId(), admins.get(0).getPassword());
        } else if (loginType.equals(DatabaseEnum.USER.getValue())) {//  用户登录
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName(loginName);
            userInfo.setPassword(MD5Utils.MD5(password));
            List<UserInfo> userInfos =userInfoDAO.listByCondition(null, userInfo);
            if (null == userInfos || userInfos.size() == 0) {
                return Result.error(ResultEnum.NOT_FOUND);
            }
            token = tokenService.getToken(userInfos.get(0).getId(), userInfos.get(0).getPassword());
        } else {
            return Result.error(ResultEnum.NO_PERMISSION);
        }
        return Result.success(token);
    }

    @Override
    public Result listByCondition(Integer pageNum, Integer pageSize, Admin admin) {
        if (null != admin) {
            String password = admin.getPassword();
            if (null != password && !"".equals(password)) {
                admin.setPassword(MD5Utils.MD5(password));
            }
        }
        if (null == pageNum || null == pageSize) {// 列表
            return Result.success(baseMapper.listByCondition(null, admin));
        } else {// 分页
            Page<Admin> page = new Page<>(pageNum, pageSize);
            page.setRecords(baseMapper.listByCondition(page, admin));
            return Result.success(page);
        }
    }
}
