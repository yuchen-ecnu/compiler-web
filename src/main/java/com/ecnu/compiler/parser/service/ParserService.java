package com.ecnu.compiler.parser.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ecnu.compiler.common.domain.Cfg;
import com.ecnu.CompilerBuilder;
import com.ecnu.compiler.component.CacheManager.Language;
import com.ecnu.compiler.component.lexer.domain.RE;
import com.ecnu.compiler.component.parser.domain.ParsingTable.LRParsingTable;
import com.ecnu.compiler.component.parser.domain.ParsingTable.ParsingTable;
import com.ecnu.compiler.component.parser.domain.PredictTable.PredictTable;
import com.ecnu.compiler.component.parser.domain.TD;
import com.ecnu.compiler.constant.Config;
import com.ecnu.compiler.constant.Constants;
import com.ecnu.compiler.constant.StatusCode;
import com.ecnu.compiler.controller.Compiler;
import com.ecnu.compiler.history.service.HistoryService;
import com.ecnu.compiler.lexical.domain.Regex;
import com.ecnu.compiler.lexical.mapper.CompilerMapper;
import com.ecnu.compiler.lexical.mapper.RegexMapper;
import com.ecnu.compiler.parser.domain.vo.*;
import com.ecnu.compiler.parser.mapper.CFGMapper;
import com.ecnu.compiler.rbac.domain.History;
import com.ecnu.compiler.rbac.domain.User;
import com.ecnu.compiler.utils.UserUtils;
import com.ecnu.compiler.utils.domain.HttpRespCode;
import com.ecnu.compiler.utils.domain.Resp;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author michaelchen
 */
@Service
public class ParserService {
    @Resource
    private HistoryService historyService;
    @Resource
    private RegexMapper regexMapper;
    @Resource
    private CFGMapper cfgMapper;
    @Resource
    private CompilerMapper compilerMapper;

    public Resp generateParserTable(int id, String text){
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
        config.setExecuteType(Constants.EXECUTE_STAGE_BY_STAGE);
        config.setParserAlgorithm(compilerVO.getParserModel());

        CompilerBuilder compilerBuilder = new CompilerBuilder();
        Language language = compilerBuilder.prepareLanguage(id, reStrList, cfgStrList,new ArrayList<String>(),new HashMap<String, String>());

        Compiler compiler = compilerBuilder.getCompilerInstance(id, config);
        if(ObjectUtils.isEmpty(compiler)){
            return new Resp(HttpRespCode.PRECONDITION_FAILED,compilerBuilder.getErrorList());
        }
        //初始化编译器
        compiler.prepare(text);
        while (compiler.getStatus().getCode()>0&&compiler.getStatus() != StatusCode.STAGE_SEMANTIC_ANALYZER){
            compiler.next();
        }

        if(compiler.getStatus().getCode()<0){
            return new Resp(HttpRespCode.PRECONDITION_FAILED,compiler.getErrorList());
        }

        Compiler.TimeHolder timeHolder = compiler.getTimeHolder();
        TimeTableVO timeTable = new TimeTableVO(timeHolder);

        ParsingTable pt = getParsingTable(language,compilerVO.getParserModel());
        TD td = compiler.getSyntaxTree();
        PredictTable pd = compiler.getPredictTable();

        User user = UserUtils.getCurrentUser();
        historyService.logUserHistory(new History(user.getId(),compilerVO.getId(),text,
                com.ecnu.compiler.utils.domain.Constants.LOG_TYPE_PARSER));
        return new Resp(HttpRespCode.SUCCESS,new ParserVO(timeTable, new TDVO(td), pt,compilerVO.getParserModel()+"",pd));
        //return new Resp(HttpRespCode.SUCCESS,new NParserVO(timeTable, new TDVO(td), new LRParserTableVO((LRParsingTable) pt),compilerVO.getParserModel()+"",pd));
    }

    private ParsingTable getParsingTable(Language language, Integer parserModel) {
        switch(parserModel){
            case Constants.PARSER_LL:
                return language.getLLParsingTable();
            case Constants.PARSER_LALR:
                return language.getLALRParsingTable();
            case Constants.PARSER_LR:
                return language.getLRParsingTable();
            case Constants.PARSER_SLR:
                return language.getSLRParsingTable();
            default:
                return null;
        }
    }
}
