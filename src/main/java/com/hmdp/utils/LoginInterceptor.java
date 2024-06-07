package com.hmdp.utils;

import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * @author Wang
 * @date 2024-04-16
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session中的用户信息
        Object user = request.getSession().getAttribute("user");
        //判断用户是否登录
        if (user == null) {
            //未登录，重定向到登录页面
            response.sendRedirect("/login.html");
            return false;
        }
        //已登录，将用户信息保存到当前线程中并放行
        //将user中的信息拷贝到UserDto中
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        UserHolder.saveUser(userDTO);
        return true;
    }
}
