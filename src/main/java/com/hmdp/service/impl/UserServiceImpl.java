package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import com.hmdp.mapper.UserMapper;
import com.hmdp.service.IUserService;
import com.hmdp.utils.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.hmdp.utils.RedisConstants.LOGIN_CODE_KEY;
import static com.hmdp.utils.RedisConstants.LOGIN_USER_TTL;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        //1.校验手机号
        String phone = loginForm.getPhone();
        if (!RegexUtils.isPhoneInvalid(phone)) {
            return Result.fail("手机号格式不正确!");
        }
        //2.校验验证码
        //改用redis存储验证码
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
        String code = loginForm.getCode();
        //2.1.判断验证码是否为空
        if (cacheCode == null) {
            return Result.fail("验证码已过期");
        }
        //2.2.判断验证码是否正确
        if (!cacheCode.equals(code)) {
            return Result.fail("验证码错误");
        }
        //3.根据手机号查询用户
        User user = query().eq("phone", phone).one();
        //3.1.判断用户是否存在
        if (user == null) {
            //3.1.1 用户不存在，注册用户
            user = creatUserWithPhone(loginForm.getPhone());
        }
        //4.将用户信息保存到redis中
        // 随机生成token，作为登录令牌
        String token = UUID.randomUUID().toString();
        // 将用户信息保存到redis中
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                    CopyOptions.create()
                    .setIgnoreNullValue(true)
                    .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString())
                    );
        String tokenKey = LOGIN_CODE_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        // 设置过期时间
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);
        //5.返回成功-TOKEN
        return Result.ok(token);

    }

    private User creatUserWithPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        //设置默认的昵称 格式如：user_88arndojw9、user_hnyhc3mjat\
        //利用正则表达式生成
        user.setNickName("user_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10));
        //将创建时间和更新时间设置为当前时间
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        //保存到数据库
        userMapper.insert(user);
        return user;

    }

    @Override
    public Result sendCode(String phone, HttpSession session) {
        //1.使用正则表达式校验手机号
        if (RegexUtils.isPhoneInvalid(phone)) {
            //1.1 不合法返回错误信息
            return Result.fail("手机号格式不正确");
        }
        //2.生成验证码
        String code = UUID.randomUUID().toString().substring(0, 6);

        //3.将验证码保存到session中
        session.setAttribute("code", code);
        //3.1 设置验证码的过期时间
        session.setMaxInactiveInterval(60);
        //4. 发送验证码
        log.debug("向手机号{}发送验证码：{}", phone, code);
        //5.返回成功
        return Result.ok();
    }

}
