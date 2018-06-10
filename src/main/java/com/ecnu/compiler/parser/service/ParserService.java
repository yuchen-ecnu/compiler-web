package com.ecnu.compiler.parser.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ecnu.CompilerBuilder;
import com.ecnu.compiler.common.domain.Cfg;
import com.ecnu.compiler.common.mapper.CFGMapper;
import com.ecnu.compiler.component.lexer.domain.RE;
import com.ecnu.compiler.constant.Config;
import com.ecnu.compiler.constant.Constants;
import com.ecnu.compiler.constant.StatusCode;
import com.ecnu.compiler.controller.Compiler;
import com.ecnu.compiler.lexical.domain.Regex;
import com.ecnu.compiler.lexical.mapper.RegexMapper;
import com.ecnu.compiler.parser.domain.TimeTableVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParserService {
    @Resource
    private RegexMapper regexMapper;
    @Resource
    private CFGMapper cfgMapper;

    public TimeTableVO generateParserTable(int id, String text){
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
        Config config = new Config();
        config.setExecuteType(Constants.EXECUTE_STAGE_BY_STAGE);

        CompilerBuilder compilerBuilder = new CompilerBuilder();
        compilerBuilder.prepareLanguage(id, reStrList, cfgStrList);

        Compiler compiler = compilerBuilder.getCompilerInstance(id, config);
        //初始化编译器
        compiler.prepare(text);
        //利用状态码判断是否刚好执行完词法分析
        while (compiler.getStatus() != StatusCode.STAGE_SEMANTIC_ANALYZER){
            compiler.next();
        }
        Compiler.TimeHolder timeHolder = compiler.getTimeHolder();
        TimeTableVO timeTable = new TimeTableVO(timeHolder.getPreprocessorTime(),
                timeHolder.getLexerTime(),timeHolder.getParserTime());

        System.out.println("Time of preprocessor is: "+timeTable.getPreprocessorTime());
        System.out.println("Time of lexer is: "+timeTable.getLexerTime());
        System.out.println("Time of parser is: "+timeTable.getParserTime());
        return timeTable;
        //return new SymbolTableVO(listVO, regexList,compiler.getErrorList());
    }
}
