package com.ecnu.compiler.parser.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ecnu.CompilerBuilder;
import com.ecnu.LanguageBuilder;
import com.ecnu.compiler.common.domain.Cfg;
import com.ecnu.compiler.component.parser.domain.ParsingTable.ParsingTable;
import com.ecnu.compiler.component.parser.domain.PredictTable.PredictTable;
import com.ecnu.compiler.constant.StatusCode;
import com.ecnu.compiler.history.service.HistoryService;
import com.ecnu.compiler.lexical.mapper.CompilerMapper;
import com.ecnu.compiler.parser.domain.TimeTableVO;
import com.ecnu.compiler.parser.mapper.CFGMapper;
import com.ecnu.compiler.component.lexer.domain.RE;
import com.ecnu.compiler.component.parser.domain.TD;
import com.ecnu.compiler.constant.Config;
import com.ecnu.compiler.constant.Constants;
import com.ecnu.compiler.controller.Compiler;
import com.ecnu.compiler.lexical.domain.Regex;
import com.ecnu.compiler.lexical.mapper.RegexMapper;
import com.ecnu.compiler.parser.domain.ParserVO;
import com.ecnu.compiler.parser.domain.TDVO;
import com.ecnu.compiler.rbac.domain.History;
import com.ecnu.compiler.rbac.domain.User;
import com.ecnu.compiler.utils.UserUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public ParserVO generateParserTable(int id, String text){
        List<Cfg> cfgList = cfgMapper.selectList(
                new EntityWrapper<Cfg>().eq("compiler_id",id)
        );
        List<Regex> regexList = regexMapper.selectList(
                new EntityWrapper<Regex>().eq("compiler_id", id)
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
        compilerBuilder.prepareLanguage(id, reStrList, cfgStrList,new ArrayList<String>(),new HashMap<String, String>());

        Compiler compiler = compilerBuilder.getCompilerInstance(id, config);
        //初始化编译器
        compiler.prepare(text);
        while (compiler.getStatus() != StatusCode.STAGE_SEMANTIC_ANALYZER){
            compiler.next();
        }

        Compiler.TimeHolder timeHolder = compiler.getTimeHolder();
        TimeTableVO timeTable = new TimeTableVO(timeHolder.getPreprocessorTime(),
                timeHolder.getLexerTime(),timeHolder.getParserTime());


        System.out.println("Time of preprocessor is: "+timeTable.getPreprocessorTime());
        System.out.println("Time of lexer is: "+timeTable.getLexerTime());
        System.out.println("Time of parser is: "+timeTable.getParserTime());

        LanguageBuilder languageBuilder = new LanguageBuilder();
        LanguageBuilder.ParserHolder parserHolder = languageBuilder.buildParserComponents(cfgStrList);
        ParsingTable pt = getParsingTable(parserHolder,compilerVO.getParserModel());
        TD td = compiler.getSyntaxTree();
        PredictTable pd = compiler.getPredictTable();

        //TODO: Handsome Zhao 补充完整，前端绘图+表格


        User user = UserUtils.getCurrentUser();
        historyService.logUserHistory(new History(user.getId(),compilerVO.getId(),text,
                com.ecnu.compiler.utils.domain.Constants.LOG_TYPE_PARSER));
        return new ParserVO(timeTable, new TDVO(td), pt,compilerVO.getParserModel()+"",pd);
    }

    private ParsingTable getParsingTable(LanguageBuilder.ParserHolder parserHolder, Integer parserModel) {
        switch(parserModel){
            case Constants.PARSER_LL:
                return parserHolder.getLLParsingTable();
            case Constants.PARSER_LALR:
                return parserHolder.getLALRParsingTable();
            case Constants.PARSER_LR:
                return parserHolder.getLRParsingTable();
            case Constants.PARSER_SLR:
                return parserHolder.getSLRParsingTable();
            default:
                return null;
        }
    }
}
