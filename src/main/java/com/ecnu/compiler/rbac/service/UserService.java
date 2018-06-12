package com.ecnu.compiler.rbac.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ecnu.compiler.common.domain.Ag;
import com.ecnu.compiler.common.domain.Cfg;
import com.ecnu.compiler.common.domain.CompilerConfiguration;
import com.ecnu.compiler.component.lexer.domain.RE;
import com.ecnu.compiler.lexical.domain.Regex;
import com.ecnu.compiler.lexical.mapper.CompilerMapper;
import com.ecnu.compiler.lexical.service.ReService;
import com.ecnu.compiler.parser.service.CfgService;
import com.ecnu.compiler.rbac.domain.Compiler;
import com.ecnu.compiler.semantic.service.AgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;

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

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public boolean addUserCompiler(CompilerConfiguration compilerConfiguration) {
        Compiler compiler = compilerConfiguration.getCompiler();
        compiler.setUsedTime(0);
        compiler.setId(null);
        int status = compilerMapper.insert(compiler);
        if (status == 0) {
            return false;
        }
        List<Regex> reList = compilerConfiguration.getReList();
        List<Cfg> cfgList = compilerConfiguration.getCfgList();
        List<Ag> agList = compilerConfiguration.getAgList();

        //set id
        for (Regex re : reList) {
            re.setId(null);
            re.setCompilerId(compiler.getId());
        }
        for (Cfg cfg : cfgList) {
            cfg.setId(null);
            cfg.setCompilerId(compiler.getId());
        }
        for (Ag ag : agList) {
            ag.setId(null);
            ag.setCompilerId(compiler.getId());
        }

        if(!ObjectUtils.isEmpty(reList)){
            reService.insertBatch(reList);
        }
        if(!ObjectUtils.isEmpty(cfgList)){
            cfgService.insertBatch(cfgList);
        }
        if(!ObjectUtils.isEmpty(agList)){
            agService.insertBatch(agList);
        }

        return true;
    }
}
