package com.ecnu.compiler.lexical.service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ecnu.CompilerBuilder;
import com.ecnu.compiler.common.service.CommonService;
import com.ecnu.compiler.component.storage.SymbolTable;
import com.ecnu.compiler.constant.Config;
import com.ecnu.compiler.constant.StatusCode;
import com.ecnu.compiler.lexical.domain.Regex;
import com.ecnu.compiler.lexical.mapper.LexicalMapper;
import com.ecnu.compiler.rbac.domain.User;
import com.ecnu.compiler.rbac.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import com.ecnu.compiler.component.lexer.domain.DFA;
import com.ecnu.compiler.component.lexer.domain.NFA;
import com.ecnu.compiler.component.lexer.domain.RE;
import com.ecnu.compiler.controller.Compiler;
import com.ecnu.compiler.constant.Constants;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

@Service
public class LexicalService {
    @Resource
    private LexicalMapper lexicalMapper;

    public List<Regex> getRegexsFromTargetLaguage(String lanugage){
        return lexicalMapper.selectList(
                new EntityWrapper<Regex>().eq("language",lanugage));
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

    public void runExample() {
        //创建一种随便的语言
        int languageId = 0;
        List<String> reStrList = new ArrayList<>();
        reStrList.add("id aab");
        reStrList.add("if if");
        List<String> productionStrList = new ArrayList<>();
        productionStrList.add("T -> id E id");
        productionStrList.add("E -> id | if T");
        //配置Config
        Config config = new Config();
        config.setExecuteType(Constants.EXECUTE_STAGE_BY_STAGE);
        //测试
        CompilerBuilder compilerBuilder = new CompilerBuilder();
        if (!compilerBuilder.checkLanguage(languageId)){
            compilerBuilder.prepareLanguage(languageId, Constants.LANGUAGE_JAVA, reStrList, productionStrList);
        }
        Compiler compiler = compilerBuilder.getCompilerInstance(languageId, config);
        //使用compiler
        //随便的一段代码
        String text = "aab aab if aab aab aab";
        //初始化编译器
        compiler.prepare(text);
        //利用状态码判断是否达到了对应的步骤
        while (compiler.getStatus() != StatusCode.STAGE_PARSER){
            compiler.next();
            System.out.println("now status is: " + compiler.getStatus().getText());
        }
    /* 当然你也可以这样来进行循环
    while (compiler.next() != StatusCode.STAGE_PARSER){
        System.out.println("now status is: " + compiler.getStatus().getText());
    }*/ //结束了
        System.out.println("Ok!!");
    }

    static public void main(String[] args){
        LexicalService l = new LexicalService();
        l.getLexicalAnalyzeTable("haha");
    }
}
