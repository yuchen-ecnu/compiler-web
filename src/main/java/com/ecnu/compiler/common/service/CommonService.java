package com.ecnu.compiler.common.service;

import com.ecnu.compiler.common.domain.DfaVO;
import com.ecnu.compiler.common.domain.NfaVO;
import com.ecnu.compiler.component.lexer.domain.DFA;
import com.ecnu.compiler.component.lexer.domain.NFA;
import com.ecnu.compiler.component.lexer.domain.RE;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class CommonService {

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
        DFA dfa = regularExpression.getDFAIndirect();
        if(ObjectUtils.isEmpty(dfa)) { return null; }
        return  new DfaVO(dfa);
    }
}
