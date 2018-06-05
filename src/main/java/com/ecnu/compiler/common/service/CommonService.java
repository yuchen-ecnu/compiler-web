package com.ecnu.compiler.common.service;

import com.ecnu.compiler.common.domain.DfaVO;
import com.ecnu.compiler.common.domain.NfaVO;
import com.ecnu.compiler.component.lexer.domain.DFA;
import com.ecnu.compiler.component.lexer.domain.NFA;
import com.ecnu.compiler.component.lexer.domain.RE;
import com.ecnu.compiler.rbac.domain.Compiler;
import com.ecnu.compiler.rbac.service.UserService;
import com.ecnu.compiler.utils.domain.Constants;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommonService {

    @Resource
    private UserService userService;
    /**
     * RE 转 NFA
     * @param re regular expression
     * @return {@link NFA}
     */
    public NfaVO RE2NFA(String re){
        RE regularExpression = new RE("common",re);
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
        RE regularExpression = new RE("common",re);
        DFA dfa = regularExpression.getDFADirectly();
        if(ObjectUtils.isEmpty(dfa)) { return null; }
        return  new DfaVO(dfa);
    }

    /**
     * 获取系统预置Compiler
     * @return
     */
    public List<Compiler> getSystemCompilers() {
        return userService.getUserCompilers(Constants.SYSTEM_ID);
    }
}
