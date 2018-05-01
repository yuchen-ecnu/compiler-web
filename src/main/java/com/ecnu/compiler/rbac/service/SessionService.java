package com.ecnu.compiler.rbac.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ecnu.compiler.rbac.domain.User;
import com.ecnu.compiler.rbac.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SessionService {
    @Resource
    private UserMapper userMapper;

    public List<User> getUsers(){
        return userMapper.selectPage(
                new Page<User>(2,10)
                ,new EntityWrapper<User>()
        );
    }
}
