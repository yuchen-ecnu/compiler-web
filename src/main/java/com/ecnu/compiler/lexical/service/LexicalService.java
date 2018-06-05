package com.ecnu.compiler.lexical.service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ecnu.CompilerBuilder;
import com.ecnu.LanguageBuilder;
import com.ecnu.compiler.common.domain.DfaVO;
import com.ecnu.compiler.component.lexer.domain.DFA;
import com.ecnu.compiler.component.lexer.domain.RE;
import com.ecnu.compiler.component.storage.ErrorList;
import com.ecnu.compiler.component.storage.SymbolTable;
import com.ecnu.compiler.component.storage.domain.Token;
import com.ecnu.compiler.constant.Config;
import com.ecnu.compiler.constant.Constants;
import com.ecnu.compiler.constant.StatusCode;
import com.ecnu.compiler.controller.Compiler;
import com.ecnu.compiler.lexical.domain.Regex;
import com.ecnu.compiler.lexical.domain.SymbolTableVO;
import com.ecnu.compiler.lexical.domain.SymbolVO;
import com.ecnu.compiler.lexical.mapper.LexicalMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sun.jvm.hotspot.debugger.cdbg.Sym;

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

    public SymbolTableVO generateSymbolTable(int id, String text, ErrorList errorList){
        List<Regex> regexesList = lexicalMapper.selectList(
                new EntityWrapper<Regex>().eq("compiler_id", id));
        List<RE> reStrList = new ArrayList<>();
        int listSize = regexesList.size();
        for(int i = 0; i < listSize; i++){
            Regex reg = regexesList.get(i);
            reStrList.add(new RE(reg.getName(), reg.getRegex()));
        }

        Config config = new Config();
        config.setExecuteType(Constants.EXECUTE_STAGE_BY_STAGE);

        CompilerBuilder compilerBuilder = new CompilerBuilder();
        if (!compilerBuilder.checkLanguage(id)){
            //只用于词法分析，暂时用不到最后一个参数，故传空列表
            //这里第二个参数要求传入baselanguage:c/c++/java
            //数据库里c++与c放在一起，故先确定用1表示c和c++ 2表示java
            String baseLanguage;
            if(id == 1)
                baseLanguage = Constants.LANGUAGE_CPLUS;
            else
                baseLanguage = Constants.LANGUAGE_JAVA;
            compilerBuilder.prepareLanguage(id, baseLanguage, reStrList, new ArrayList<String>());
        }

        Compiler compiler = compilerBuilder.getCompilerInstance(id, config);
        //初始化编译器
        compiler.prepare(text);
        //利用状态码判断是否刚好执行完词法分析
        while (compiler.getStatus() != StatusCode.STAGE_PARSER){
            compiler.next();
        }
        errorList = compiler.getErrorList();
        List<Token> list = compiler.getSymbolTable().getTokens();
        int tokensListSize = list.size();
        List<SymbolVO> listVO = new ArrayList<>();
        for(int i = 0; i < tokensListSize; i++){
            listVO.add(new SymbolVO(list.get(i), i + 1));
        }

        return new SymbolTableVO(listVO, regexesList);
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
