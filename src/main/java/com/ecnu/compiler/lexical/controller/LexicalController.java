package com.ecnu.compiler.lexical.controller;
/**
 * Controller编写规则：
 *   1、所有路径均按节划分，每一节均为 单个 名词
 *   2、路径结尾以反斜杠结束
 *   3、符合Restful接口规范
 *        GET：请求资源，不修改服务器数据
 *        POST：向服务器新增资源或修改资源
 *        DELETE：删除服务器资源
 *   4、返回结果均以Resp对象返回，框架统一转换为json
 *   5、代码中不得出现汉字，返回信息统一规范到HttpRespCode中
 *      若Resp中没有所需要的文字，请在ConstantsMsg中添加文字
 *   6、所有函数请在头部标明作者，以便代码回溯
 *   7、使用 @Resource 注解自动装配 Service
 *
 *   @author Handsome Zhao
 */

import com.ecnu.compiler.common.domain.DfaVO;
import com.ecnu.compiler.component.storage.ErrorList;
import com.ecnu.compiler.component.storage.SymbolTable;
import com.ecnu.compiler.lexical.domain.LexerParam;
import com.ecnu.compiler.lexical.domain.Regex;
import com.ecnu.compiler.lexical.domain.SymbolTableVO;
import com.ecnu.compiler.lexical.domain.SymbolVO;
import com.ecnu.compiler.lexical.service.LexicalService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecnu.compiler.utils.domain.HttpRespCode;
import com.ecnu.compiler.utils.domain.Resp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 语法控制器
 *
 *
 * @author Handsome Zhao
 */
@RestController
@RequestMapping(value = "/lexical")
public class LexicalController {
    @Resource
    private LexicalService lexicalService;

    /**
     *  根据语言获得其正则表达式列表
     */
    @RequestMapping(value = "/regex/", method = RequestMethod.GET)
    public ResponseEntity<Resp> getRegexsFromTargetLaguage(@RequestParam("language") String language){
        //params error
        if(ObjectUtils.isEmpty(language)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Resp());
        }
        List<Regex> list = lexicalService.getRegrexsFromTargetLanguage(language);
        if(ObjectUtils.isEmpty(list)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Resp());
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(new Resp(HttpRespCode.SUCCESS, list));
        }
    }

    /**
     *  根据正则表达式ID获取DFA
     */
    @RequestMapping(value = "/rid2dfa/", method = RequestMethod.GET)
    public ResponseEntity<Resp> getDfaFromRegexById(@RequestParam("id") Integer id){
        //params error
        if(ObjectUtils.isEmpty(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Resp());
        }
        DfaVO dfaVO = lexicalService.getDFAbyRegexId(id);
        if(ObjectUtils.isEmpty(dfaVO)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Resp());
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(new Resp(HttpRespCode.SUCCESS, dfaVO));
        }
    }

    /**
     * 词法分析器
     * @param id
     * @param text
     * @return
     */
    @RequestMapping(value = "/lexer/", method = RequestMethod.GET)
    public ResponseEntity<Resp> text2SymbolTable(@RequestParam("id") int id, @RequestParam("text") String text) {
        //params error
        if(ObjectUtils.isEmpty(id)||ObjectUtils.isEmpty(text)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Resp());
        }
        ErrorList errorList = new ErrorList();
        SymbolTableVO symbolTable = lexicalService.generateSymbolTable(id, text, errorList);
        if(errorList.getErrorMsgList().size() == 0 && symbolTable != null){
            return ResponseEntity.status(HttpStatus.OK).body(new Resp(HttpRespCode.SUCCESS,symbolTable));
        }else{
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body(new Resp(HttpRespCode.METHOD_NOT_ALLOWED,errorList.getErrorMsgList()));
        }
    }

}
