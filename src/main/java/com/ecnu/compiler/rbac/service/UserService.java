package com.ecnu.compiler.rbac.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ecnu.compiler.common.domain.CompilerConfiguration;
import com.ecnu.compiler.lexical.mapper.CompilerMapper;
import com.ecnu.compiler.lexical.service.ReService;
import com.ecnu.compiler.parser.service.CfgService;
import com.ecnu.compiler.rbac.domain.Compiler;
import com.ecnu.compiler.semantic.service.AgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author michaelchen
 */
@Service
public class UserService {
    @Resource
    private CompilerMapper compilerMapper;
    @Resource
    private ReService reService;
    @Resource
    private CfgService cfgService;
    @Resource
    private AgService agService;

    public List<Compiler> getUserCompilers(int uid) {
        return compilerMapper.selectList(
                new EntityWrapper<Compiler>().eq("user_id", uid)
        );
    }

    public boolean modifyUserCompiler(CompilerConfiguration compilerConfiguration) {
        Compiler compiler = compilerConfiguration.getCompiler();
        int status = compilerMapper.updateById(compiler);
        if(status==0){ return false; }
        reService.insertOrUpdateAllColumnBatch(compilerConfiguration.getReList());
        cfgService.insertOrUpdateAllColumnBatch(compilerConfiguration.getCfgList());
        agService.insertOrUpdateAllColumnBatch(compilerConfiguration.getAgList());
        return true;
    }

    public boolean addUserCompiler(CompilerConfiguration compilerConfiguration) {
        Compiler compiler = compilerConfiguration.getCompiler();
        compiler.setUsedTime(0);
        int status = compilerMapper.insert(compiler);
        if(status==0){ return false; }
        reService.insertOrUpdateAllColumnBatch(compilerConfiguration.getReList());
        cfgService.insertOrUpdateAllColumnBatch(compilerConfiguration.getCfgList());
        agService.insertOrUpdateAllColumnBatch(compilerConfiguration.getAgList());
        return true;
    }
}
