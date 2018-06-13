package com.ecnu.compiler.lexical.service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ecnu.CompilerBuilder;
import com.ecnu.compiler.common.domain.Cfg;
import com.ecnu.compiler.common.domain.DfaVO;
import com.ecnu.compiler.component.lexer.domain.DFA;
import com.ecnu.compiler.component.lexer.domain.RE;
import com.ecnu.compiler.component.storage.SymbolTable;
import com.ecnu.compiler.component.storage.domain.Token;
import com.ecnu.compiler.constant.Config;
import com.ecnu.compiler.constant.Constants;
import com.ecnu.compiler.constant.StatusCode;
import com.ecnu.compiler.controller.Compiler;
import com.ecnu.compiler.history.service.HistoryService;
import com.ecnu.compiler.lexical.domain.Regex;
import com.ecnu.compiler.lexical.domain.SymbolTableVO;
import com.ecnu.compiler.lexical.domain.SymbolVO;
import com.ecnu.compiler.lexical.mapper.CompilerMapper;
import com.ecnu.compiler.lexical.mapper.RegexMapper;
import com.ecnu.compiler.parser.mapper.CFGMapper;
import com.ecnu.compiler.rbac.domain.History;
import com.ecnu.compiler.rbac.domain.User;
import com.ecnu.compiler.rbac.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author michaelchen
 */
@Service
public class LexicalService{
    @Resource
    private HistoryService historyService;
    @Resource
    private CompilerMapper compilerMapper;
    @Resource
    private RegexMapper regexMapper;
    @Resource
    private CFGMapper cfgMapper;

    public List<Regex> getRegexFromTargetLanguage(String language){
        return regexMapper.selectList(
                new EntityWrapper<Regex>().eq("language", language));
    }

    public SymbolTableVO generateSymbolTable(int id, String text){

        //init compiler
        com.ecnu.compiler.rbac.domain.Compiler compilerVO = compilerMapper.selectById(id);
        if(ObjectUtils.isEmpty(compilerVO)){ return null; }
        compilerVO.setUsedTime(compilerVO.getUsedTime()+1);
        compilerMapper.updateById(compilerVO);

        //init re
        List<Regex> regexList = regexMapper.selectList(
                new EntityWrapper<Regex>().eq("compiler_id", id));
        List<RE> reStrList = new ArrayList<>();
        for (Regex reg : regexList) {

            reStrList.add(new RE(reg.getName(), reg.getRegex(), reg.getType()));
        }

        //init cfg
        List<Cfg> cfgList = cfgMapper.selectList(
                new EntityWrapper<Cfg>().eq("compiler_id",id)
        );
        List<String> cfgStrList = new ArrayList<>();
        for(Cfg cfg : cfgList){
            cfgStrList.add(cfg.getCfgContent());
        }

        Config config = new Config();
        config.setExecuteType(Constants.EXECUTE_STAGE_BY_STAGE);
        config.setParserAlgorithm(compilerVO.getParserModel());

        CompilerBuilder compilerBuilder = new CompilerBuilder();
        compilerBuilder.prepareLanguage(id, reStrList, cfgStrList);

        Compiler compiler = compilerBuilder.getCompilerInstance(id, config);
        //初始化编译器
        compiler.prepare(text);
        //利用状态码判断是否刚好执行完词法分析
        while (compiler.getStatus() != StatusCode.STAGE_PARSER){
            compiler.next();
        }
        SymbolTable sb = compiler.getSymbolTable();
        if(sb == null){
            return null;
        }
        List<Token> list = sb.getTokens();
        int tokensListSize = list.size();
        List<SymbolVO> listVO = new ArrayList<>();
        for(int i = 0; i < tokensListSize; i++){
            listVO.add(new SymbolVO(list.get(i), i + 1));
        }
        User user = UserUtils.getCurrentUser();
        historyService.logUserHistory(new History(user.getId(),compilerVO.getId(),text,
                com.ecnu.compiler.utils.domain.Constants.LOG_TYPE_LEXER));
        return new SymbolTableVO(listVO, regexList,compiler.getErrorList());
    }

    public DfaVO getDFAbyRegexId(Integer id){
        Regex regex = regexMapper.selectById(id);

        RE regularExpression = new RE("lexical", regex.getRegex(), regex.getType());
        DFA dfa = regularExpression.getDFAIndirect();
        if(ObjectUtils.isEmpty(dfa)) { return null; }
        return  new DfaVO(dfa);
    }
}
