package com.ecnu.compiler.common.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ecnu.compiler.common.domain.*;
import com.ecnu.compiler.parser.mapper.CFGMapper;
import com.ecnu.compiler.component.lexer.domain.DFA;
import com.ecnu.compiler.component.lexer.domain.NFA;
import com.ecnu.compiler.component.lexer.domain.RE;
import com.ecnu.compiler.lexical.domain.Regex;
import com.ecnu.compiler.lexical.mapper.CompilerMapper;
import com.ecnu.compiler.lexical.mapper.RegexMapper;
import com.ecnu.compiler.rbac.domain.Compiler;
import com.ecnu.compiler.rbac.domain.User;
import com.ecnu.compiler.rbac.service.UserService;
import com.ecnu.compiler.rbac.utils.UserUtils;
import com.ecnu.compiler.semantic.mapper.AGMapper;
import com.ecnu.compiler.utils.domain.Constants;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommonService {

    @Resource
    private UserService userService;

    @Resource
    private CompilerMapper compilerMapper;
    @Resource
    private RegexMapper regexMapper;
    @Resource
    private CFGMapper cfgMapper;
    @Resource
    private AGMapper agMapper;
    /**
     * RE 转 NFA
     * @param re regular expression
     * @return {@link NFA}
     */
    public NfaVO RE2NFA(String re){
        RE regularExpression = new RE("common",re,2);
        NFA nfa = regularExpression.getNFA();
        if(ObjectUtils.isEmpty(nfa)) { return null; }
        return new NfaVO(nfa);
    }


    /**
     * RE 转 DFA
     * @param re regular expression
     * @return {@link DFA}
     */
    public DfaVO RE2DFA(String re){
        RE regularExpression = new RE("common",re,2);
        DFA dfa = regularExpression.getDFADirectly();
        dfa = DFA.DFA2MinDFA(dfa);
        if(ObjectUtils.isEmpty(dfa)) { return null; }
        return  new DfaVO(dfa);
    }

    /**
     * 获取系统预置Compiler
     */
    public List<Compiler> getSystemCompilers() {
        return userService.getUserCompilers(Constants.SYSTEM_ID);
    }

    public CompilerConfiguration getSystemCompilerConfiguration(int id) {
        Compiler compiler = compilerMapper.selectById(id);
        if(ObjectUtils.isEmpty(compiler)){ return null; }
        User user = UserUtils.getCurrentUser();
        if(compiler.getUserId()!= Constants.SYSTEM_ID
                && user!=null && !user.getId().equals(compiler.getUserId())){
            return null;
        }
        List<Regex> reList = regexMapper.selectList(
                new EntityWrapper<Regex>().eq("compiler_id",compiler.getId())
        );
        List<Cfg> cfgList = cfgMapper.selectList(
                new EntityWrapper<Cfg>().eq("compiler_id",compiler.getId())
        );
        List<Ag> agList = agMapper.selectList(
                new EntityWrapper<Ag>().eq("compiler_id",compiler.getId())
        );

        return new CompilerConfiguration(compiler,reList,cfgList,agList);
    }
}
