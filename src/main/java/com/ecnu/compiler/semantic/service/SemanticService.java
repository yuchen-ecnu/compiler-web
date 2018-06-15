package com.ecnu.compiler.semantic.service;

import com.ecnu.compiler.history.service.HistoryService;
import com.ecnu.compiler.lexical.mapper.CompilerMapper;
import com.ecnu.compiler.lexical.mapper.RegexMapper;
import com.ecnu.compiler.parser.domain.ParserVO;
import com.ecnu.compiler.parser.mapper.CFGMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SemanticService {
    @Resource
    private HistoryService historyService;
    @Resource
    private RegexMapper regexMapper;
    @Resource
    private CFGMapper cfgMapper;
    @Resource
    private CompilerMapper compilerMapper;


    public ParserVO semanticAnalyser(Integer lan, String txt) {
        return null;
    }
}
