package com.ecnu.compiler.rbac.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ecnu.compiler.rbac.domain.Compiler;
import com.ecnu.compiler.rbac.mapper.CompilerMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    private CompilerMapper compilerMapper;

    public List<Compiler> getUserCompilers(int uid) {
        return compilerMapper.selectList(
                new EntityWrapper<Compiler>().eq("user_id", uid)
        );
    }
}
