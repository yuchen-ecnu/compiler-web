package com.ecnu.compiler.semantic.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ecnu.CompilerBuilder;
import com.ecnu.compiler.common.domain.Ag;
import com.ecnu.compiler.common.domain.Cfg;
import com.ecnu.compiler.component.lexer.domain.RE;
import com.ecnu.compiler.constant.Config;
import com.ecnu.compiler.constant.Constants;
import com.ecnu.compiler.constant.StatusCode;
import com.ecnu.compiler.controller.Compiler;
import com.ecnu.compiler.history.service.HistoryService;
import com.ecnu.compiler.lexical.domain.Regex;
import com.ecnu.compiler.lexical.mapper.CompilerMapper;
import com.ecnu.compiler.lexical.mapper.RegexMapper;
import com.ecnu.compiler.parser.domain.vo.TimeTableVO;
import com.ecnu.compiler.parser.mapper.CFGMapper;
import com.ecnu.compiler.rbac.domain.History;
import com.ecnu.compiler.rbac.domain.User;
import com.ecnu.compiler.semantic.domain.Action;
import com.ecnu.compiler.semantic.domain.SemanticVO;
import com.ecnu.compiler.semantic.mapper.AGMapper;
import com.ecnu.compiler.semantic.mapper.ActionMapper;
import com.ecnu.compiler.utils.UserUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SemanticService {
    @Resource
    private HistoryService historyService;
    @Resource
    private RegexMapper regexMapper;
    @Resource
    private CFGMapper cfgMapper;
    @Resource
    private AGMapper agMapper;
    @Resource
    private ActionMapper actionMapper;
    @Resource
    private CompilerMapper compilerMapper;


    public SemanticVO semanticAnalyser(Integer id, String text) {
        List<Regex> regexList = regexMapper.selectList(
                new EntityWrapper<Regex>().eq("compiler_id", id)
        );
        List<Cfg> cfgList = cfgMapper.selectList(
                new EntityWrapper<Cfg>().eq("compiler_id",id)
        );
        List<Ag> agList = agMapper.selectList(
                new EntityWrapper<Ag>().eq("compiler_id",id)
        );
        List<Action> actionList = actionMapper.selectList(
                new EntityWrapper<Action>().eq("compiler_id",id)
        );
        List<RE> reStrList = new ArrayList<>();
        for (Regex reg : regexList) {
            reStrList.add(new RE(reg.getName(),reg.getRegex(),reg.getType()));
        }
        List<String> cfgStrList = new ArrayList<>();
        for(Cfg cfg : cfgList){
            cfgStrList.add(cfg.getCfgContent());
        }
        List<String> agStrList = new ArrayList<>();
        for(Ag ag : agList){
            cfgStrList.add(ag.getAgContent());
        }
        Map acList = new HashMap<String,String>();
        for(Action ac : actionList){
            acList.put(ac.getActionName(),ac.getActionContent());
        }

        com.ecnu.compiler.rbac.domain.Compiler compilerVO = compilerMapper.selectById(id);
        compilerVO.setUsedTime(compilerVO.getUsedTime()+1);
        compilerMapper.updateById(compilerVO);

        Config config = new Config();
        config.setExecuteType(Constants.EXECUTE_STAGE_BY_STAGE);
        config.setParserAlgorithm(compilerVO.getParserModel());

        CompilerBuilder compilerBuilder = new CompilerBuilder();
        compilerBuilder.prepareLanguage(id, reStrList, cfgStrList,agStrList,acList);

        Compiler compiler = compilerBuilder.getCompilerInstance(id, config);
        //初始化编译器
        compiler.prepare(text);
        while (compiler.getStatus().getCode()>0 &&compiler.getStatus() != StatusCode.STAGE_BACKEND){
            compiler.next();
        }

        Compiler.TimeHolder timeHolder = compiler.getTimeHolder();
        TimeTableVO timeTable = new TimeTableVO(timeHolder.getPreprocessorTime(),
                timeHolder.getLexerTime(),timeHolder.getParserTime());
        List<String> compilerActionList = compiler.getActionList();

        System.out.println("Time of preprocessor is: "+timeTable.getPreprocessorTime());
        System.out.println("Time of lexer is: "+timeTable.getLexerTime());
        System.out.println("Time of parser is: "+timeTable.getParserTime());
        System.out.println("Time of semantic analyser is: "+timeTable.getParserTime());

        User user = UserUtils.getCurrentUser();
        historyService.logUserHistory(new History(user.getId(),compilerVO.getId(),text,
                com.ecnu.compiler.utils.domain.Constants.LOG_TYPE_PARSER));
        return new SemanticVO(timeTable,compilerActionList);
    }
}
