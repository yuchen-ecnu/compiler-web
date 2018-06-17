package com.ecnu.compiler.rbac.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ecnu.compiler.rbac.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Michael Chen
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
