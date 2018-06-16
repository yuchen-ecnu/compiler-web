package com.ecnu.compiler.semantic.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ecnu.compiler.common.domain.Ag;
import com.ecnu.compiler.semantic.domain.Action;
import com.ecnu.compiler.semantic.mapper.AGMapper;
import com.ecnu.compiler.semantic.mapper.ActionMapper;
import org.springframework.stereotype.Service;

/**
 * @author michaelchen
 */
@Service
public class ActionService extends ServiceImpl<ActionMapper,Action> {
    
}
