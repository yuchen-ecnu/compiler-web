package com.ecnu.compiler.lexical.service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ecnu.CompilerBuilder;
import com.ecnu.compiler.common.domain.DfaVO;
import com.ecnu.compiler.component.lexer.domain.DFA;
import com.ecnu.compiler.component.lexer.domain.RE;
import com.ecnu.compiler.component.storage.SymbolTable;
import com.ecnu.compiler.constant.Config;
import com.ecnu.compiler.constant.Constants;
import com.ecnu.compiler.constant.StatusCode;
import com.ecnu.compiler.controller.Compiler;
import com.ecnu.compiler.lexical.domain.Regex;
import com.ecnu.compiler.lexical.mapper.LexicalMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LexicalService {
    @Resource
    private LexicalMapper lexicalMapper;

    public List<Regex> getRegrexsFromTargetLanguage(String language){
        return lexicalMapper.selectList(
                new EntityWrapper<Regex>().eq("language", language));
    }

    public SymbolTable generateSymbolTable(String text,String language){
        //创建一种随便的语言
        int languageId = 0;
        List<Regex> regexs= getRegrexsFromTargetLanguage(language);
        List<RE> reStrList = new ArrayList<>();
        for(Regex r:regexs){
            reStrList.add(new RE(r.getName(),r.getRegex()));
        }
        List<String> productionStrList = new ArrayList<>();
        //配置Config
        Config config = new Config();
        config.setExecuteType(Constants.EXECUTE_STAGE_BY_STAGE);
        //测试
        CompilerBuilder compilerBuilder = new CompilerBuilder();
        if (!compilerBuilder.checkLanguage(languageId)){
            compilerBuilder.prepareLanguage(
                    languageId,
                    language.equals("c/c++")?Constants.LANGUAGE_CPLUS:Constants.LANGUAGE_JAVA,
                    reStrList, productionStrList
            );
        }
        Compiler compiler = compilerBuilder.getCompilerInstance(languageId, config);
        compiler.prepare(text);
        //利用状态码判断是否达到了对应的步骤
        while (compiler.getStatus() != StatusCode.STAGE_PARSER){
            compiler.next();
            System.out.println("now status is: " + compiler.getStatus().getText());
        }
        return compiler.getSymbolTable();
    }

    public DfaVO getDFAbyRegexId(Integer id){
        Regex regex = lexicalMapper.selectById(id);
        RE regularExpression = new RE("lexical", regex.getRegex());
        DFA dfa = regularExpression.getDFAIndirect();
        if(ObjectUtils.isEmpty(dfa)) { return null; }
        return  new DfaVO(dfa);
    }

    public void getLexicalAnalyzeTable(String code){
        SymbolTable symbolTable = new SymbolTable();
        Map<String, DFA> map = new HashMap<String, DFA>();
        map.put("x2", new RE("x2", "aab").getDFAIndirect());
        map.put("x3", new RE("x3", "if").getDFAIndirect());
        List<String> list = new ArrayList<>();
        list.add("aab");
        list.add("if");
        list.add("aab");
        list.add("aab");
        symbolTable.build(map, list);

    }
}
