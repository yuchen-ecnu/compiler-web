package com.ecnu.compiler.common.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ecnu.CompilerBuilder;
import com.ecnu.compiler.common.domain.*;
import com.ecnu.compiler.component.CacheManager.Language;
import com.ecnu.compiler.component.parser.domain.ParsingTable.LLParsingTable;
import com.ecnu.compiler.component.parser.domain.ParsingTable.LRParsingTable;
import com.ecnu.compiler.component.parser.domain.ParsingTable.ParsingTable;
import com.ecnu.compiler.constant.Config;
import com.ecnu.compiler.parser.domain.vo.LLParserTableVO;
import com.ecnu.compiler.parser.domain.vo.LRParserTableVO;
import com.ecnu.compiler.parser.domain.vo.NParserVO;
import com.ecnu.compiler.parser.domain.vo.TDVO;
import com.ecnu.compiler.parser.mapper.CFGMapper;
import com.ecnu.compiler.component.lexer.domain.DFA;
import com.ecnu.compiler.component.lexer.domain.NFA;
import com.ecnu.compiler.component.lexer.domain.RE;
import com.ecnu.compiler.lexical.domain.Regex;
import com.ecnu.compiler.lexical.mapper.CompilerMapper;
import com.ecnu.compiler.lexical.mapper.RegexMapper;
import com.ecnu.compiler.parser.service.ParserService;
import com.ecnu.compiler.rbac.domain.Compiler;
import com.ecnu.compiler.rbac.domain.User;
import com.ecnu.compiler.rbac.mapper.UserMapper;
import com.ecnu.compiler.rbac.service.UserService;
import com.ecnu.compiler.semantic.domain.Action;
import com.ecnu.compiler.semantic.mapper.ActionMapper;
import com.ecnu.compiler.utils.UserUtils;
import com.ecnu.compiler.semantic.mapper.AGMapper;
import com.ecnu.compiler.utils.domain.Constants;
import com.ecnu.compiler.utils.domain.HttpRespCode;
import com.ecnu.compiler.utils.domain.Resp;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CommonService {

    @Resource
    private UserService userService;
    @Resource
    private ParserService parserService;

    @Resource
    private CompilerMapper compilerMapper;
    @Resource
    private RegexMapper regexMapper;
    @Resource
    private CFGMapper cfgMapper;
    @Resource
    private AGMapper agMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ActionMapper actionMapper;
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

    public List<User> getSiteRank() {
        List<String> conditions =  new ArrayList<>();
        conditions.add("times");
        return userMapper.selectPage(
                new Page<User>(1, 5)
                ,new EntityWrapper<User>().orderDesc(conditions));
    }

    public Resp getParserTable(Integer id,Integer type) {

        List<Regex> regexList = regexMapper.selectList(
                new EntityWrapper<Regex>().eq("compiler_id", id)
        );
        List<Cfg> cfgList = cfgMapper.selectList(
                new EntityWrapper<Cfg>().eq("compiler_id",id)
        );
        List<RE> reStrList = new ArrayList<>();
        for (Regex reg : regexList) {
            reStrList.add(new RE(reg.getName(),reg.getRegex(),reg.getType()));
        }
        List<String> cfgStrList = new ArrayList<>();
        for(Cfg cfg : cfgList){
            cfgStrList.add(cfg.getCfgContent());
        }

        com.ecnu.compiler.rbac.domain.Compiler compilerVO = compilerMapper.selectById(id);
        compilerVO.setUsedTime(compilerVO.getUsedTime()+1);
        compilerMapper.updateById(compilerVO);

        Config config = new Config();
        config.setExecuteType(com.ecnu.compiler.constant.Constants.EXECUTE_STAGE_BY_STAGE);
        config.setParserAlgorithm(type);

        CompilerBuilder compilerBuilder = new CompilerBuilder();
        Language language = compilerBuilder.prepareLanguage(id, reStrList, cfgStrList,new ArrayList<String>(),new HashMap<String, String>());
        ParsingTable pb = parserService.getParsingTable(language,type);
        if(ObjectUtils.isEmpty(pb)){
            return new Resp(HttpRespCode.PRECONDITION_FAILED,compilerBuilder.getErrorList());
        }
        if(type == com.ecnu.compiler.constant.Constants.PARSER_LL)
            return new Resp(HttpRespCode.SUCCESS, new NParserVO(null, null, new LLParserTableVO((LLParsingTable) pb),
                    type + "", null));
        else
            return new Resp(HttpRespCode.SUCCESS, new NParserVO(null, null, new LRParserTableVO((LRParsingTable) pb),
                    type + "", null));
    }

    public Resp getCompilerConfiguration(int id) {
        Compiler compiler = compilerMapper.selectById(id);
        if(ObjectUtils.isEmpty(compiler)){ return new Resp(HttpRespCode.NOT_FOUND); }
        User user = UserUtils.getCurrentUser();
        if(compiler.getUserId()!= Constants.SYSTEM_ID){
            if(ObjectUtils.isEmpty(user)){
                return new Resp(HttpRespCode.UNAUTHORIZED);
            }else if(!user.getId().equals(compiler.getUserId())){
                return new Resp(HttpRespCode.FORBIDDEN);
            }
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
        List<Action> actionList = actionMapper.selectList(
                new EntityWrapper<Action>().eq("compiler_id",compiler.getId())
        );

        return new Resp(HttpRespCode.SUCCESS,new CompilerConfiguration(compiler,reList,cfgList,agList,actionList));
    }
}
