package com.hmdp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.entity.User;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface IUserService extends IService<User> {

    /**
     * 用户登录
     * @param loginForm 登录表单
     * @param session session
     * @return 登录结果
     * @auther wanglitao
     * @date 2024-04-16
     */
    Result login(LoginFormDTO loginForm, HttpSession session);

    /**
     * 发送验证码
     * @param phone 手机号
     * @param session session-用于存储验证码
     * @return 发送结果
     * @auther wanglitao
     * @date 2024-04-16
     */
    Result sendCode(String phone, HttpSession session);
}
